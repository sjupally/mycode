package com.abhaya.vehicle.tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.CitizenDetails;
import com.abhaya.vehicle.tracking.data.model.DriverDutyDetails;
import com.abhaya.vehicle.tracking.data.model.EmergencyContactNumbers;
import com.abhaya.vehicle.tracking.data.model.TravelTracking;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.model.TripDetailsView;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;
import com.abhaya.vehicle.tracking.data.repos.DriverDutyDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.EmergencyContactNumbersRepository;
import com.abhaya.vehicle.tracking.data.repos.SpecificationUtils;
import com.abhaya.vehicle.tracking.data.repos.TravelTrackingRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsViewRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsViewSpecifications;
import com.abhaya.vehicle.tracking.events.EntityReadEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadTripDetailsSetEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDataEvent;
import com.abhaya.vehicle.tracking.exception.ResourceNotFoundException;
import com.abhaya.vehicle.tracking.model.EsTripDetails;
import com.abhaya.vehicle.tracking.repository.ESTripDetailsRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.Geocoder;
import com.abhaya.vehicle.tracking.util.GoogleURLShortening;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.util.SMSConfiguration;
import com.abhaya.vehicle.tracking.utils.DateUtils;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.TripDetailsVO;
import com.abhaya.vehicle.tracking.vos.TripDetailsViewVO;
import com.abhaya.vehicle.tracking.vos.TripVO;

import lombok.extern.slf4j.Slf4j;

public interface TripDetailsService 
{
	public ResponseVO save(TripVO tripVO);
	public TripDetailsVO getTripLatLang(String vehicleNumber);
	public boolean endTrip(String vehicleNumber);
	public void updateDocumentOnEndTrip(String vehicleNumber);
	public PageReadEvent<TripDetailsViewVO> readTripDetails(ReadTripDetailsSetEvent request);
	public TripDetailsVO getCurrentLocation(String vehicleNumber);
	public TripDetailsVO isTripExist(String vehicleNumber, String citizenMobileNumber);
	public void shareMyRoute(String citizenMobileNumber);
	public EntityReadEvent<TripDetailsViewVO> readDataById(ReadVehicleDataEvent request);

	@Slf4j
	@Service
	public class impl implements TripDetailsService
	{
		@Value("${route.url}")
		private String routeShareUrl;

		@Autowired private CitizenService citizenService;
		@Autowired private SMSConfiguration smsConfiguration;
		@Autowired private VehicleDetailsService vehicleDetailsService;
		@Autowired private TripDetailsRepository tripDetailsRepository;
		@Autowired private EmergencyContactNumbersRepository repository;
		@Autowired private DriverDutyDetailsRepository driverDutyRepository;
		@Autowired private TripDetailsViewRepository tripDetailsViewRepository;
		@Autowired private TravelTrackingRepository travelTrackingRepository;
		@Autowired private ESTripDetailsRepository esTripDetailsRepository;

		@Override
		public ResponseVO save(TripVO tripDetailsVO) 
		{
			ResponseVO responseVO = new ResponseVO();
			try
			{
				VehicleDetails vehicleDetails = vehicleDetailsService.checkVehicle(tripDetailsVO.getRcNumber());
				if (vehicleDetails.getIsDeviceMapped() && true) //true --> here check IoT device status by making request to IoT device (vehicleDetails.getSerialNumber())
				{
					tripDetailsRepository.closeExistingTrip(vehicleDetails);
					List<DriverDutyDetails> driverOnBoardings = driverDutyRepository.getByVehicleDetails(vehicleDetails);
					if (driverOnBoardings != null && driverOnBoardings.size() > 0)
					{
						CitizenDetails citizenDetails = citizenService.checkCitizen(tripDetailsVO.getCitizenMobileNumber());
						TripDetails tripDetails = TripDetails.builder()
								.citizenDetails(citizenDetails)
								.driverDetails(driverOnBoardings.get(0).getDriverDetails())
								.isTripClosed(Boolean.FALSE)
								.requestTime(DateUitls.getCurrentSystemTimestamp())
								.sourceLatLang(tripDetailsVO.getSourceLatLang())
								.destiLatLang(tripDetailsVO.getDestiLatLang())
								.vehicleDetails(vehicleDetails)
								.requestId(DateUtils.getUniqueId())
								.sourceLocation(Geocoder.getFormattedAddress(tripDetailsVO.getSourceLatLang()))
								.destiLocation(Geocoder.getFormattedAddress(tripDetailsVO.getDestiLatLang()))
								.build();
						tripDetails = tripDetailsRepository.save(tripDetails);
						tripDetailsRepository.flush();
						responseVO.setCode(tripDetails.getId());
						responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
						
						if (tripDetailsVO.isShareRoute())
						{
							shareRoute(tripDetailsVO.getCitizenMobileNumber(),tripDetails);
						}
						indexTrip(tripDetails);
					}
					else
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_404);
						responseVO.setMessage(Constants.DRIVER_ON_BOARD_MSG);
					}
				}
				else
				{
					responseVO.setCode(Constants.ResponseMessages.CODE_404);
					responseVO.setMessage("IoT Device is not active");
				}
			}
			catch (Exception e)
			{
				log.info("Exception while saving Trip Details :: " + e.getCause(),e);
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			}
			return responseVO;
		}

		private void indexTrip(TripDetails tripDetails) 
		{
			EsTripDetails esTripDetails = EsTripDetails.builder()
					.citizenMobileNumber(tripDetails.getCitizenDetails().getMobileNumber())
					.destiLatLang(tripDetails.getDestiLatLang())
					.destiLocation(tripDetails.getDestiLocation())
					.dlNumber(tripDetails.getDriverDetails().getDlNumber())
					.driverAddress(String.join(",",tripDetails.getDriverDetails().getCity().getName(),tripDetails.getDriverDetails().getDistricts().getName(),tripDetails.getDriverDetails().getState().getName()))
					.driverContactNumber(tripDetails.getDriverDetails().getContactNumber())
					.driverName(tripDetails.getDriverDetails().getDriverName())
					.id(tripDetails.getId().toString())
					.isTripClosed(Boolean.FALSE)
					.ownerContactNumber(tripDetails.getVehicleDetails().getOwnerDetails().getOwnerContactNumber())
					.ownerName(tripDetails.getVehicleDetails().getOwnerDetails().getOwnerName())
					.rcNumber(tripDetails.getVehicleDetails().getRcNumber())
					.remarks(tripDetails.getRemarks())
					.requestId(tripDetails.getRequestId())
					.requestTime(tripDetails.getRequestTime())
					.rfId(tripDetails.getDriverDetails().getRfId())
					.serialNumber(tripDetails.getVehicleDetails().getSerialNumber())
					.sourceLatLang(tripDetails.getSourceLatLang())
					.sourceLocation(tripDetails.getSourceLocation())
					.travelMode(tripDetails.getTravelMode())
					.vehicleAddress(String.join(",",tripDetails.getVehicleDetails().getCity().getName(),tripDetails.getVehicleDetails().getDistricts().getName(),tripDetails.getVehicleDetails().getState().getName()))
					.build();
			esTripDetailsRepository.save(esTripDetails);
		}

		@Override
		public TripDetailsVO getTripLatLang(String vehicleNumber) 
		{
			log.info("Vehicle Number " + vehicleNumber);
			TripDetails tripDetails = tripDetailsRepository.getTripByVehicleNumber(vehicleNumber);
			if (!StringUtils.isEmpty(tripDetails))
			{
				return TripDetailsVO.builder()
						.sourceLatLang(tripDetails.getSourceLatLang())
						.destiLatLang(tripDetails.getDestiLatLang())
						.id(tripDetails.getId())
						.requestTime(tripDetails.getRequestTime())
						.citizenMobileNumber(tripDetails.getCitizenDetails().getMobileNumber())
						.build();
			}
			else
			{
				return null;
			}
		}
		@Override
		public TripDetailsVO isTripExist(String vehicleNumber, String citizenMobileNumber) 
		{
			TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumberNVehicleNumber(citizenMobileNumber,vehicleNumber);
			if (!StringUtils.isEmpty(tripDetails))
			{
				return TripDetailsVO.builder()
					.sourceLatLang(tripDetails.getSourceLatLang())
					.destiLatLang(tripDetails.getDestiLatLang())
					.id(tripDetails.getId())
					.requestTime(tripDetails.getRequestTime())
					.citizenMobileNumber(tripDetails.getCitizenDetails().getMobileNumber())
					.isTripExist(Boolean.TRUE)
					.build();
			}
			else
			{
				return TripDetailsVO.builder()
				.isTripExist(Boolean.FALSE)
				.build();
			}
		}
		
		@Override
		public TripDetailsVO getCurrentLocation(String vehicleNumber) 
		{
			VehicleDetails vehicleDetails = vehicleDetailsService.checkVehicle(vehicleNumber);
			List<TravelTracking> travelTrackings = travelTrackingRepository.getBySerialNumberAndDate(vehicleDetails.getSerialNumber(),DateUitls.getCurrentDateAsString("dd/MM/yyyy"));
			if (travelTrackings != null && travelTrackings.size() > 0)
			{
				return TripDetailsVO.builder()
						.sourceLatLang(String.join(",", travelTrackings.get(0).getLatitude(),travelTrackings.get(0).getLangitude()))
						.build();
			}
			else
			{
				return null;
			}
		}

		@Override
		public boolean endTrip(String vehicleNumber) 
		{
			try	
			{
				log.info("End trip vehicleNumber  " + vehicleNumber);
				TripDetails tripDetails = tripDetailsRepository.getTripByVehicleNumber(vehicleNumber);
				tripDetails.setCloseTime(DateUitls.getCurrentSystemTimestamp());
				tripDetails.setTripClosed(true);
				tripDetailsRepository.save(tripDetails);
				
				return Boolean.TRUE;
			}
			catch (Exception e) 
			{
				log.info("Exception while End Trip  :: "+e.getCause(),e);
				return Boolean.FALSE;
			}
		}
		@Override
		public void updateDocumentOnEndTrip(String vehicleNumber) 
		{
			try	
			{
				List<EsTripDetails> esTripDetails = esTripDetailsRepository.findByRcNumber(vehicleNumber);
				if (esTripDetails != null && esTripDetails.size() > 0)
				{
					EsTripDetails esTripDetail = esTripDetails.stream().filter(x -> !x.isTripClosed()).findAny().orElse(null);
					if (!StringUtils.isEmpty(esTripDetail))
					{
						esTripDetail.setCloseTime(DateUitls.getCurrentSystemTimestamp());
						esTripDetail.setTripClosed(Boolean.TRUE);
						esTripDetailsRepository.save(esTripDetail);
					}
				}
				
			}
			catch (Exception e) 
			{
				log.info("Exception while End Trip  :: "+e.getCause(),e);
			}
			
		}
		
		@Override
		public PageReadEvent<TripDetailsViewVO> readTripDetails(ReadTripDetailsSetEvent request)
		{
			TripDetailsViewSpecifications tripDetailsSpecifications = new TripDetailsViewSpecifications(request.getDlNumber(),request.getRcNumber(),request.getIsTripClosed(),request.getCitizenMobileNumber(),
					request.getStartDate(),request.getStateId(),request.getDistrictId(),request.getCityId(),request.getSearchValue(), request.getDriverContactNumber());

			Page<TripDetailsView> dbContent = tripDetailsViewRepository.findAll(tripDetailsSpecifications,TripDetailsViewSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize(),
											  SpecificationUtils.sortBySortKey(request.getProperty(), request.getDirection())));

			List<TripDetailsViewVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), TripDetailsViewVO.class);
			Page<TripDetailsViewVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
		@Override
		public EntityReadEvent<TripDetailsViewVO> readDataById(ReadVehicleDataEvent request)
		{
			TripDetailsView record = tripDetailsViewRepository.getOne(request.getId());
			if (record == null) 
			{
				throw new ResourceNotFoundException("Resource Not Found", request.getId());
			}
			ModelMapper modelMapper = new ModelMapper();
			TripDetailsViewVO vo = modelMapper.map(record, TripDetailsViewVO.class);
			return new EntityReadEvent<>(vo);
		}
		
		private void shareRoute(String citizenContactNumber,TripDetails tripDetails) 
		{
			List<EmergencyContactNumbers> contactNumbers = repository.getByCitizenMobileNumber(citizenContactNumber);
			String numbersToShare = contactNumbers.stream().map(EmergencyContactNumbers::getEmergencyContactNumber).collect(Collectors.joining(","));
			String rcNumber = tripDetails.getVehicleDetails().getRcNumber();

			String shortnURL = GoogleURLShortening.shortenUrl(routeShareUrl + "vehicleNumber=" + rcNumber + "&citizenMobileNumber=" + citizenContactNumber + "&isShared=true");
			log.info("shortnURL " +  shortnURL);
			String message  = String.format(Constants.SHARE_LOCATION_MSG, rcNumber,tripDetails.getDriverDetails().getDriverName(),tripDetails.getDriverDetails().getContactNumber(),shortnURL);

			log.info("numbersToShare " + numbersToShare + " message " + message);
			smsConfiguration.sendSms(numbersToShare.toString(), message); 
		}

		@Override
		public void shareMyRoute(String citizenMobileNumber) 
		{
			TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(citizenMobileNumber);
			shareRoute(citizenMobileNumber,tripDetails);
		}
	}
}
