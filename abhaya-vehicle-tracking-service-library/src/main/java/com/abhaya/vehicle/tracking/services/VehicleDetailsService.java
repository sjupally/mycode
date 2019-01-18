package com.abhaya.vehicle.tracking.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.Districts;
import com.abhaya.vehicle.tracking.data.model.OwnerDetails;
import com.abhaya.vehicle.tracking.data.model.RouteDeviation;
import com.abhaya.vehicle.tracking.data.model.State;
import com.abhaya.vehicle.tracking.data.model.StatusInfo;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;
import com.abhaya.vehicle.tracking.data.repos.CityRepository;
import com.abhaya.vehicle.tracking.data.repos.DistrictsRepository;
import com.abhaya.vehicle.tracking.data.repos.OwnerDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.RouteDeviationRepository;
import com.abhaya.vehicle.tracking.data.repos.SpecificationUtils;
import com.abhaya.vehicle.tracking.data.repos.StateRepository;
import com.abhaya.vehicle.tracking.data.repos.StatusInfoRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.VehicleDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.VehicleDetailsSpecifications;
import com.abhaya.vehicle.tracking.data.repos.VehicleInstSummaryReportSQLRepository;
import com.abhaya.vehicle.tracking.events.CreateVehicleDetailsEvent;
import com.abhaya.vehicle.tracking.events.EntityReadEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDataEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDetailsSetEvent;
import com.abhaya.vehicle.tracking.exception.ResourceNotFoundException;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.utils.VehicleInstSummaryByDistrictVO;
import com.abhaya.vehicle.tracking.vos.DistrictwiseVehiclesVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.StateWiseVehicleVO;
import com.abhaya.vehicle.tracking.vos.VehicleDetailsVO;
import com.abhaya.vehicle.tracking.vos.VehicleIntsallationSummaryVO;
import com.abhaya.vehicle.tracking.vos.VehicleStoppageSummaryVO;

import lombok.extern.slf4j.Slf4j;

public interface VehicleDetailsService 
{
	public VehicleDetails checkVehicle(String rcNumber);
	public ResponseVO save(CreateVehicleDetailsEvent event);
	public ResponseVO mapIoTDevice(String vehicleNumber, String serialNumber);
	public PageReadEvent<VehicleDetailsVO> readVehicleData(ReadVehicleDetailsSetEvent request);
	public VehicleIntsallationSummaryVO getVehicleIntsallationSummary();
	public VehicleStoppageSummaryVO getVehicleStoppageSummary();
	public EntityReadEvent<VehicleDetailsVO> readDataById(ReadVehicleDataEvent request);
	public List<StateWiseVehicleVO> getStateWideAnalytics(Long stateId);

	public List<VehicleInstSummaryByDistrictVO> getVehicleIntSummaryByDistrict(ReadVehicleDetailsSetEvent request);

	@Service
	@Slf4j
	public class impl implements VehicleDetailsService
	{
		@Autowired private CityRepository cityRepository;
		@Autowired private StateRepository stateRepository;
		@Autowired private VehicleDetailsRepository repository;
		@Autowired private DistrictsRepository districtsRepository;
		@Autowired private OwnerDetailsRepository ownerDetailsRepository;
		@Autowired private TripDetailsRepository tripDetailsRepository;
		@Autowired private RouteDeviationRepository routeDeviationRepository;
		@Autowired private StatusInfoRepository statusInfoRepository;
		@Autowired private VehicleInstSummaryReportSQLRepository vehicleInstSummaryReportSQLRepository;

		@Override
		public PageReadEvent<VehicleDetailsVO> readVehicleData(ReadVehicleDetailsSetEvent request) 
		{
			VehicleDetailsSpecifications specifications = new VehicleDetailsSpecifications(request.getMobileNumber(),request.getRcNumber(),
					request.getSerialNumber(),request.getSearchValue(),request.getIsDeviceMapped(),
					request.getDistrictId(), request.getCityId(),request.getDate());
			Page<VehicleDetails> dbContent = repository.findAll(specifications,VehicleDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize(),
					SpecificationUtils.sortBySortKey(request.getProperty(), request.getDirection())));
			
			List<VehicleDetailsVO> content = new ArrayList<>();
			ModelMapper modelMapper = new ModelMapper();
			for (VehicleDetails record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				content.add(modelMapper.map(record, VehicleDetailsVO.class)); 
			}
			Page<VehicleDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		@Override
		public EntityReadEvent<VehicleDetailsVO> readDataById(ReadVehicleDataEvent request)
		{
			VehicleDetails record = repository.getOne(request.getId());
			if (record == null) 
			{
				throw new ResourceNotFoundException("Resource Not Found", request.getId());
			}
			ModelMapper modelMapper = new ModelMapper();
			VehicleDetailsVO vo = modelMapper.map(record, VehicleDetailsVO.class);
			return new EntityReadEvent<>(vo);
		}

		@Override
		public ResponseVO save(CreateVehicleDetailsEvent event) 
		{
			ResponseVO responseVO = new ResponseVO();
			try
			{
				VehicleDetailsVO vehicleDetailsVO = event.getVehicleDetailsVO();
				VehicleDetails vehicleDetails = null;
				ModelMapper modelMapper = new ModelMapper();
				if (!StringUtils.isEmpty(vehicleDetailsVO.getId())) 
				{
					vehicleDetails = repository.getOne(vehicleDetailsVO.getId());
				}
				else
				{
					vehicleDetails = repository.getByRCNumber(vehicleDetailsVO.getRcNumber());
					if (vehicleDetails != null)
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("Vehicle with " + vehicleDetailsVO.getRcNumber() + " " + Constants.ResponseMessages.MESSAGE_400);
					}
					else
					{
						vehicleDetails = new VehicleDetails();
					}
				}
				vehicleDetails = modelMapper.map(vehicleDetailsVO, VehicleDetails.class);
				OwnerDetails ownerDetails = ownerDetailsRepository.getByContactNumber(vehicleDetailsVO.getOwnerContactNumber());
				if (ownerDetails == null)
				{
					ownerDetails = new OwnerDetails();
				}
				ownerDetails.setOwnerContactNumber(vehicleDetailsVO.getOwnerContactNumber());
				ownerDetails.setOwnerName(vehicleDetailsVO.getOwnerName());
				ownerDetails = ownerDetailsRepository.save(ownerDetails);

				vehicleDetails.setOwnerDetails(ownerDetails);
				vehicleDetails.setDistricts(districtsRepository.getOne(vehicleDetailsVO.getDistrictsId()));
				vehicleDetails.setCity(cityRepository.getOne(vehicleDetailsVO.getCityId()));
				vehicleDetails.setState(stateRepository.getOne(vehicleDetailsVO.getStateId()));
				vehicleDetails.setIsDeviceMapped(vehicleDetailsVO.isDeviceMapped());
				vehicleDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				repository.save(vehicleDetails);

				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			catch (Exception e)
			{
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
				log.info("Exception while saving Vehicle Details :: " + e.getCause() ,e);
			}
			return responseVO;
		}

		@Override
		public VehicleDetails checkVehicle(String rcNumber) 
		{
			return repository.getByRCNumber(rcNumber);
		}

		@Override
		public ResponseVO mapIoTDevice(String vehicleNumber, String serialNumber) 
		{
			ResponseVO responseVO = new ResponseVO();
			VehicleDetails vehicleDetails = repository.getBySerialNumber(serialNumber);
			if (vehicleDetails != null)
			{
				responseVO.setCode(Constants.ResponseMessages.CODE_400);
				responseVO.setMessage("IoT Device is already mapped to Vehicle " + vehicleDetails.getRcNumber());
			}
			else
			{
				vehicleDetails = repository.getByRCNumber(vehicleNumber);
				vehicleDetails.setSerialNumber(serialNumber);
				vehicleDetails.setIsDeviceMapped(Boolean.TRUE);
				vehicleDetails.setDeviceMappedDate(DateUitls.getCurrentSystemTimestamp());
				repository.save(vehicleDetails);

				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			return responseVO;
		}

		@Override
		public VehicleIntsallationSummaryVO getVehicleIntsallationSummary() 
		{
			List<VehicleDetails> vehicleDetails = repository.findAll();

			Long totalInstalledCount = vehicleDetails.stream().filter(vehicle -> vehicle.getIsDeviceMapped()).count();
			Long todaysInstalled = vehicleDetails.stream().filter(vehicle -> vehicle.getIsDeviceMapped() && new Date(vehicle.getDeviceMappedDate().getTime()).toLocalDate().equals(LocalDate.now())).count();
			Long totalPending = vehicleDetails.stream().filter(vehicle -> !vehicle.getIsDeviceMapped()).count();
			
			return VehicleIntsallationSummaryVO.builder()
					.todaysInstalled(todaysInstalled)
					.totalInstalled(totalInstalledCount)
					.totalPending(totalPending)
					.build();
		}

		@Override
		public VehicleStoppageSummaryVO getVehicleStoppageSummary() 
		{
			return VehicleStoppageSummaryVO.builder()
					.todaysVehicles(10L)
					.totalVehicles(15L)
					.build();
		}
		
		@Override
		public List<StateWiseVehicleVO> getStateWideAnalytics(Long stateId)
		{
			List<VehicleDetails> vehicleDetails = null;
			if (!StringUtils.isEmpty(stateId))
			{
				vehicleDetails = repository.findByStateId(stateId);
			}
			else
			{
				vehicleDetails = repository.findAll();
			}
			Map<State, List<VehicleDetails>> groupByState = vehicleDetails.stream().collect(Collectors.groupingBy(VehicleDetails::getState));
			
			List<StateWiseVehicleVO> states = new ArrayList<>();
			for (State s : groupByState.keySet())
			{
				StateWiseVehicleVO stateWiseVehicleVO = new StateWiseVehicleVO();
				List<TripDetails> stateWisetripDetails = tripDetailsRepository.findStateWiseLiveVehicles(s);
				List<RouteDeviation> stateWiseRouteDeviations = routeDeviationRepository.findStateWiseDeviatedVehicles(s);
				int i = 0;
				for (VehicleDetails v : groupByState.get(s))
				{
					if (!StringUtils.isEmpty(v.getSerialNumber()))
					{
						List<StatusInfo> statusInfos = statusInfoRepository.findStateWiseIgnitionStatus(s,v.getSerialNumber());
						if (statusInfos != null && statusInfos.size() > 0)
						{
							i = 1 + i;
						}
					}
				}
				stateWiseVehicleVO.setTotalVehicles(groupByState.get(s).size());
				stateWiseVehicleVO.setIgnitionOff(i);
				stateWiseVehicleVO.setLiveTracking(stateWisetripDetails.size());
				stateWiseVehicleVO.setRouteDeviation(stateWiseRouteDeviations.size());
				stateWiseVehicleVO.setStateName(s.getName());
				//===============================================================================================
				List<VehicleDetails> districWiseGroup =  groupByState.get(s);
				Map<Districts, List<VehicleDetails>> districtsCount = districWiseGroup.stream().collect(Collectors.groupingBy(VehicleDetails::getDistricts));
				
				Map<String ,Integer> trips = new HashMap<>();
				for (TripDetails tripDetails : stateWisetripDetails)
				{
					String name = tripDetails.getVehicleDetails().getDistricts().getName();
					if (trips.containsKey(name))
					{
						trips.put(name, trips.get(name) + 1);
					}
					else
					{
						trips.put(name, 1);
					}
				}
				Map<String ,Integer> deviations = new HashMap<>();
				for (RouteDeviation deviation : stateWiseRouteDeviations)
				{
					String name = deviation.getTripDetails().getVehicleDetails().getDistricts().getName();
					if (deviations.containsKey(name))
					{
						deviations.put(name, deviations.get(name) + 1);
					}
					else
					{
						deviations.put(name, 1);
					}
				}
				List<DistrictwiseVehiclesVO> districtwiseVehiclesVOs = new ArrayList<>();
				for (Districts d : districtsCount.keySet())
				{
					DistrictwiseVehiclesVO districtwiseVehiclesVO = new DistrictwiseVehiclesVO();
					districtwiseVehiclesVO.setTotalVehicles(districtsCount.get(d).size());
					districtwiseVehiclesVO.setDistrictName(d.getName());
					districtwiseVehiclesVO.setLiveTracking(trips.get(d.getName()) != null ? trips.get(d.getName()) : 0);
					districtwiseVehiclesVO.setRouteDeviation(deviations.get(d.getName()) != null ? deviations.get(d.getName()) : 0);

					int j = 0;
					for (VehicleDetails v : districtsCount.get(d))
					{
						if (!StringUtils.isEmpty(v.getSerialNumber()))
						{
							List<StatusInfo> statusInfos = statusInfoRepository.findDistrictsWiseIgnitionStatus(d,v.getSerialNumber());
							if (statusInfos != null && statusInfos.size() > 0)
							{
								j = j + i;
							}
						}
					}
					districtwiseVehiclesVO.setIgnitionOff(j);
					districtwiseVehiclesVOs.add(districtwiseVehiclesVO);
				}
				stateWiseVehicleVO.setDistricts(districtwiseVehiclesVOs);
				states.add(stateWiseVehicleVO);
			}
			return states;
		}

		@Override
		public List<VehicleInstSummaryByDistrictVO> getVehicleIntSummaryByDistrict(ReadVehicleDetailsSetEvent request) {
			return vehicleInstSummaryReportSQLRepository.getVehicleStatusInfo(request.getDistrictId());
		}
	}
}
