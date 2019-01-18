package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;
import com.abhaya.vehicle.tracking.data.model.WatchVehicle;
import com.abhaya.vehicle.tracking.data.model.WatchVehicleView;
import com.abhaya.vehicle.tracking.data.repos.SpecificationUtils;
import com.abhaya.vehicle.tracking.data.repos.VehicleDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.WatchVehicleRepository;
import com.abhaya.vehicle.tracking.data.repos.WatchVehicleSpecifications;
import com.abhaya.vehicle.tracking.data.repos.WatchVehicleViewRepository;
import com.abhaya.vehicle.tracking.events.CreateWatchVehicleEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadWatchVehicleDataSetEvent;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.WatchVehicleVO;

import lombok.extern.slf4j.Slf4j;

public interface WatchVehicleService 
{
	
	public ResponseVO save(CreateWatchVehicleEvent event);
	public PageReadEvent<WatchVehicleVO> readData(ReadWatchVehicleDataSetEvent request);
	
	@Slf4j
	@Service
	public class impl implements WatchVehicleService
	{

		@Autowired private WatchVehicleRepository repository;
		@Autowired private VehicleDetailsRepository vehicleDetailsRepository;
		@Autowired private WatchVehicleViewRepository watchVehicleViewRepository;

		@Override
		public ResponseVO save(CreateWatchVehicleEvent event) 
		{
			ResponseVO responseVO = new ResponseVO();
			try
			{
				WatchVehicleVO watchVehicleVO = event.getWatchVehicleVO();
				VehicleDetails vehicleDetails = vehicleDetailsRepository.getByRCNumber(watchVehicleVO.getRcNumber());
				if (StringUtils.isEmpty(vehicleDetails))
				{
					responseVO.setCode(Constants.ResponseMessages.CODE_404);
					responseVO.setMessage("Vehicle with " + watchVehicleVO.getRcNumber() + " " + Constants.ResponseMessages.MESSAGE_404);
				}
				else
				{
					WatchVehicle watchVehicle = repository.getByRCNumber(watchVehicleVO.getRcNumber());
					if (!StringUtils.isEmpty(watchVehicle))
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("Vehicle with " + watchVehicleVO.getRcNumber() + " " + Constants.ResponseMessages.MESSAGE_400);
					}
					else
					{
						watchVehicle = new WatchVehicle();
					}
					watchVehicle.setVehicleDetails(vehicleDetails);
					watchVehicle.setLocation(watchVehicleVO.getLocation());
					watchVehicle.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
					repository.save(watchVehicle);
					responseVO.setCode(Constants.ResponseMessages.CODE_200);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				}
			}
			catch (Exception e)
			{
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
				log.info("Exception while saving Watch Vehicle Details :: " + e.getCause() ,e);
			}
			return responseVO;
		}
		
		@Override
		public PageReadEvent<WatchVehicleVO> readData(ReadWatchVehicleDataSetEvent request)
		{
			WatchVehicleSpecifications specifications = new WatchVehicleSpecifications(request.getRcNumber(),request.getSerialNumber(),request.getStateId(),request.getDistrictId(),request.getCityId());
			Page<WatchVehicleView> dbContent = watchVehicleViewRepository.findAll(specifications,WatchVehicleSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize(),
					SpecificationUtils.sortBySortKey(request.getProperty(), request.getDirection())));
            
            List<WatchVehicleVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), WatchVehicleVO.class);
            Page<WatchVehicleVO> page = new PageImpl<>(content, request.getPageable(), dbContent != null ? dbContent.getTotalElements() : 0);
            return new PageReadEvent<>(page);
		}
	}
}
