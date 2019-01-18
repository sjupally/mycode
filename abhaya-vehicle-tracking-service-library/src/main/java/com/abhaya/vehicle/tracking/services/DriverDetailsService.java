package com.abhaya.vehicle.tracking.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.DriverDetails;
import com.abhaya.vehicle.tracking.data.repos.CityRepository;
import com.abhaya.vehicle.tracking.data.repos.DistrictsRepository;
import com.abhaya.vehicle.tracking.data.repos.DriverDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.DriverDetailsSpecifications;
import com.abhaya.vehicle.tracking.data.repos.SpecificationUtils;
import com.abhaya.vehicle.tracking.data.repos.StateRepository;
import com.abhaya.vehicle.tracking.events.CreateDriverDetailsDataEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDetailsSetEvent;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.vos.DriverDetailsVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

public interface DriverDetailsService 
{
	public ResponseVO mapRFCard(CreateDriverDetailsDataEvent event);
	public ResponseVO save(CreateDriverDetailsDataEvent createDriverDetailsDataEvent);
	public PageReadEvent<DriverDetailsVO> readDriverData(ReadDriverDetailsSetEvent request);
	public ResponseVO addPhoto(Long driverId, MultipartFile file) throws IOException;

	@Service
	@Slf4j
	public class impl implements DriverDetailsService
	{
		@Autowired private CityRepository cityRepository;
		@Autowired private StateRepository stateRepository;
		@Autowired private DriverDetailsRepository repository;
		@Autowired private DistrictsRepository districtsRepository;

		@Override
		public ResponseVO save(CreateDriverDetailsDataEvent createDriverDetailsDataEvent) 
		{
			ResponseVO responseVO = new ResponseVO();
			try 
			{
				DriverDetailsVO driverDetailsVO = createDriverDetailsDataEvent.getDriverDetailsVO();
				ModelMapper modelMapper = new ModelMapper();
				DriverDetails driverDetails = null;
				
				if (!StringUtils.isEmpty(driverDetailsVO.getId())) 
				{
					driverDetails = repository.getOne(driverDetailsVO.getId());
				}
				else
				{
					driverDetails = repository.getByDLNumber(driverDetailsVO.getDlNumber());
					if (driverDetails != null)
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("Driver with " + driverDetailsVO.getDlNumber() + " " + Constants.ResponseMessages.MESSAGE_400);
					}
					else
					{
						driverDetails = new DriverDetails();
					}
				}
				driverDetails = modelMapper.map(driverDetailsVO, DriverDetails.class);
				driverDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				driverDetails.setDistricts(districtsRepository.getOne(driverDetailsVO.getDistrictsId()));
				driverDetails.setCity(cityRepository.getOne(driverDetailsVO.getCityId()));
				driverDetails.setState(stateRepository.getOne(driverDetailsVO.getStateId()));
				repository.save(driverDetails);

				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			} 
			catch (Exception e) 
			{
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
				log.info("Exception while saving Driver Details :: " + e.getCause() ,e);
			}
			return responseVO;
		}

		@Override
		public PageReadEvent<DriverDetailsVO> readDriverData(ReadDriverDetailsSetEvent request) 
		{
			Page<DriverDetails> dbContent = repository.findAll(new DriverDetailsSpecifications(request.getDriverName(),request.getDlNumber(),
					request.getSearchValue(),request.getRfId(),request.getDistrictId(), request.getCityId(),request.getDate(), request.getDriverContactNumber()),
					DriverDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize(),
					SpecificationUtils.sortBySortKey(request.getProperty(), request.getDirection())));
			
			List<DriverDetailsVO> content = new ArrayList<>();
			for (DriverDetails record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				ModelMapper modelMapper = new ModelMapper();
				DriverDetailsVO details = modelMapper.map(record, DriverDetailsVO.class);
				details.setImage(record.getImage());
				content.add(details); 
			}
			Page<DriverDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public ResponseVO mapRFCard(CreateDriverDetailsDataEvent event) 
		{
			ResponseVO responseVO = new ResponseVO();
			DriverDetailsVO driverDetailsVO = event.getDriverDetailsVO();

			DriverDetails driverDetails = repository.getByRFID(driverDetailsVO.getRfId());
			if (driverDetails != null)
			{
				responseVO.setCode(Constants.ResponseMessages.CODE_400);
				responseVO.setMessage("RF Id already mapped with DL Number  " + driverDetails.getDlNumber());
			}
			else
			{
				driverDetails = repository.getByDLNumber(driverDetailsVO.getDlNumber());
				driverDetails.setRfId(driverDetailsVO.getRfId());
				repository.save(driverDetails);
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			return responseVO;
		}

		@Override
		public ResponseVO addPhoto(Long driverId, MultipartFile file) throws IOException {
			ResponseVO responseVO = new ResponseVO();
			return repository.findById(driverId).map(driver -> {
				try {
					driver.setImage(file.getBytes());
				} catch (IOException e) {
					responseVO.setCode(Constants.ResponseMessages.CODE_500);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
					log.info("Exception while adding Driver Photo :: " + e.getCause() ,e);
				}
				repository.save(driver);
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				return responseVO;
	        }).orElse(responseVO);
		}
	}
}
