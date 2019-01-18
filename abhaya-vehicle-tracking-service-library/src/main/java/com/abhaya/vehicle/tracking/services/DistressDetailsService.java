package com.abhaya.vehicle.tracking.services;

import java.text.SimpleDateFormat;
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
import com.abhaya.vehicle.tracking.data.model.DistressDetails;
import com.abhaya.vehicle.tracking.data.model.DistressDetailsView;
import com.abhaya.vehicle.tracking.data.model.EmergencyContactNumbers;
import com.abhaya.vehicle.tracking.data.model.TravelTracking;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.repos.DistressDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.DistressDetailsSpecifications;
import com.abhaya.vehicle.tracking.data.repos.DistressDetailsViewRepository;
import com.abhaya.vehicle.tracking.data.repos.EmergencyContactNumbersRepository;
import com.abhaya.vehicle.tracking.data.repos.TravelTrackingRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsRepository;
import com.abhaya.vehicle.tracking.events.EntityReadEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDistressDetailsSetEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDataEvent;
import com.abhaya.vehicle.tracking.exception.ResourceNotFoundException;
import com.abhaya.vehicle.tracking.model.ESDistressDetails;
import com.abhaya.vehicle.tracking.repository.ESDistressDetailsRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.GoogleURLShortening;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.util.SMSConfiguration;
import com.abhaya.vehicle.tracking.utils.DateUtils;
import com.abhaya.vehicle.tracking.vos.DistressDetailsVO;

import lombok.extern.slf4j.Slf4j;

public interface DistressDetailsService 
{
	public void sendSms(String citizenContactNumber);
	public boolean save(String citizenMobileNumber, String eventType);
	public PageReadEvent<DistressDetailsVO> readData(ReadDistressDetailsSetEvent request);
	public EntityReadEvent<DistressDetailsVO> readDataById(ReadVehicleDataEvent request);
	public void update(String citizenMobileNumber);
	public void updateDistressStatus(Long distressId);

	@Slf4j
	@Service
	public class impl implements DistressDetailsService
	{
		@Value("${route.url}")
		private String routeShareUrl;
		@Autowired private SMSConfiguration smsConfiguration;
		@Autowired private DistressDetailsRepository repository;
		@Autowired private TripDetailsRepository tripDetailsRepository;
		@Autowired private TravelTrackingRepository travelTrackingRepository;
		@Autowired private DistressDetailsViewRepository viewRepository;
		@Autowired private EmergencyContactNumbersRepository contactNumbersRepository;
		@Autowired private ESDistressDetailsRepository esDistressDetailsRepository;

		@Override
		public boolean save(String citizenMobileNumber, String eventType) 
		{
			try
			{
				String packetDate = "" ,packetTime = "";
				TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(citizenMobileNumber);
				if (tripDetails != null) {
					String currentLocation = tripDetails.getSourceLatLang();
					List<TravelTracking> travelTrackings = travelTrackingRepository.getByTrip(tripDetails);
					if (!travelTrackings.isEmpty())
					{
						TravelTracking tracking = travelTrackings.stream().findFirst().get();
						currentLocation = String.join(",", tracking.getLatitude(),tracking.getLangitude());
						packetDate = tracking.getPacketDate();
						packetTime = tracking.getPacketTime();
					}
					DistressDetails distressDetails = DistressDetails.builder()
							.createdDate(DateUitls.getCurrentSystemTimestamp())
							.distressLocation(currentLocation)
							.packetDate(!StringUtils.isEmpty(packetDate) ? packetDate : DateUtils.getCurrentDateAsString("dd/MM/yyyy"))
							.packetTime(!StringUtils.isEmpty(packetTime) ? packetTime : DateUtils.getTimestamp(new SimpleDateFormat("hh:mm:ss")))
							.isClosed(Boolean.FALSE)
							.tripDetails(tripDetails)
							.eventType(eventType)
							.build();
					distressDetails = repository.save(distressDetails);
					createIndex(tripDetails, distressDetails);
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			}
			catch (Exception e)
			{
				log.info("Exception while saving Distress Alert :: " + e.getMessage(), e);
				return Boolean.FALSE;
			}
		}
		private void createIndex(TripDetails tripDetails, DistressDetails distressDetails) 
		{
			ESDistressDetails esDistressDetails = ESDistressDetails.builder()
					.citizenMobileNumber(tripDetails.getCitizenDetails().getMobileNumber())
					.createdDate(DateUitls.getCurrentTimeInStringFormat())
					.destination(tripDetails.getDestiLocation())
					.dlNumber(tripDetails.getDriverDetails().getDlNumber())
					.driverContactNumber(tripDetails.getDriverDetails().getContactNumber().toString())
					.driverName(tripDetails.getDriverDetails().getDriverName())
					.id(distressDetails.getId().toString())
					.ownerContactNumber(tripDetails.getVehicleDetails().getOwnerDetails().getOwnerContactNumber().toString())
					.ownerName(tripDetails.getVehicleDetails().getOwnerDetails().getOwnerName())
					.rcNumber(tripDetails.getVehicleDetails().getRcNumber())
					.rfId(tripDetails.getDriverDetails().getRfId())
					.serialNumber(tripDetails.getVehicleDetails().getSerialNumber())
					.source(tripDetails.getSourceLatLang())
					.tripId(tripDetails.getId().toString())
					.triprequestId(tripDetails.getRequestId())
					.distressLocation(distressDetails.getDistressLocation())
					.packetDate(distressDetails.getPacketDate())
					.packetTime(distressDetails.getPacketTime())
					.build();
			esDistressDetailsRepository.save(esDistressDetails);
		}
		
		@Override
		public void sendSms(String citizenMobileNumber) 
		{
			TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(citizenMobileNumber);
			List<EmergencyContactNumbers> contactNumbers = contactNumbersRepository.getByCitizenMobileNumber(citizenMobileNumber);
			String numbersToShare = contactNumbers.stream().map(EmergencyContactNumbers::getEmergencyContactNumber).collect(Collectors.joining(","));
			String rcNumber = tripDetails.getVehicleDetails().getRcNumber();
			
			String shortnURL = GoogleURLShortening.shortenUrl(routeShareUrl + "vehicleNumber=" + rcNumber + "&citizenMobileNumber=" + citizenMobileNumber + "&isShared=true");
			log.info("shortnURL " + shortnURL);
			String message  = String.format(Constants.DISTRESS_MSG, rcNumber,tripDetails.getDriverDetails().getDriverName(),tripDetails.getDriverDetails().getContactNumber(),shortnURL);

			log.info("numbersToShare " + numbersToShare + " message " + message);
			smsConfiguration.sendSms(numbersToShare, message);
		}

		@Override
		public PageReadEvent<DistressDetailsVO> readData(ReadDistressDetailsSetEvent request) 
		{
			DistressDetailsSpecifications specifications = new DistressDetailsSpecifications(request.getSerialNumber(),request.getRcNumber(),request.getDlNumber(),request.getCitizenMobileNumber(),request.getIsClosed(),
					request.getEventType(),request.getStateId(),request.getDistrictId(),request.getCityId(),request.getSearchValue(), request.getSearchDate(), request.getTripId());
			Page<DistressDetailsView> dbContent = viewRepository.findAll(specifications,DistressDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			List<DistressDetailsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), DistressDetailsVO.class);
			Page<DistressDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public EntityReadEvent<DistressDetailsVO> readDataById(ReadVehicleDataEvent request) 
		{
			DistressDetailsView record = viewRepository.getOne(request.getId());
			if (record == null) 
			{
				throw new ResourceNotFoundException("Resource Not Found", request.getId());
			}
			ModelMapper modelMapper = new ModelMapper();
			DistressDetailsVO vo = modelMapper.map(record, DistressDetailsVO.class);
			return new EntityReadEvent<>(vo);
		}
		@Override
		public void update(String citizenMobileNumber) 
		{
			TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(citizenMobileNumber);
			DistressDetails distressDetails = repository.getByTrip(tripDetails);
			distressDetails.setClosed(Boolean.TRUE);
			
			repository.save(distressDetails);
			
		}
		
		@Override
		public void updateDistressStatus(Long distressId) 
		{
			DistressDetails distressDetails = repository.getOne(distressId);
			distressDetails.setClosed(Boolean.TRUE);
			repository.save(distressDetails);
		}
	}
}
