package com.abhaya.vehicle.tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.components.ComboComponent;
import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.City;
import com.abhaya.vehicle.tracking.data.repos.CityRepository;
import com.abhaya.vehicle.tracking.data.repos.DistrictsRepository;
import com.abhaya.vehicle.tracking.data.repos.StateRepository;
import com.abhaya.vehicle.tracking.events.CreateCitiesEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadCitiesSetEvent;
import com.abhaya.vehicle.tracking.vos.CityVO;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CityService implements ComboComponent
{
	@Autowired private CityRepository cityRepository;
	@Autowired private DistrictsRepository districtsRepository;
	@Autowired private StateRepository stateRepository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<City> dbContent = cityRepository.findByDistrictId(Long.parseLong(extraParams[0]));
		List<ComboDataVO> content = dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), p.getCode())).collect(Collectors.toList());
		return content;
	}
	
	public ResponseVO save(CreateCitiesEvent event) 
	{
		ResponseVO responseVO = new ResponseVO();
		try
		{
			CityVO cityVO = event.getCityVO();
			City city = null;
			ModelMapper modelMapper = new ModelMapper();
			if (!StringUtils.isEmpty(cityVO.getId()))
			{
				city = cityRepository.getOne(cityVO.getId());
			}
			else
			{
				city = new City();
			}
			city = modelMapper.map(cityVO, City.class);
			city.setState(stateRepository.getOne(cityVO.getStateId()));
			city.setDistricts(districtsRepository.getOne(cityVO.getDistrictId()));
			
			cityRepository.save(city);

			responseVO.setCode(Constants.ResponseMessages.CODE_200);
			responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
		}
		catch (Exception e)
		{
			responseVO.setCode(Constants.ResponseMessages.CODE_500);
			responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			log.info("Exception while saving Cities :: " + e.getCause() ,e);
		}
		return responseVO;
	} 
	
	public PageReadEvent<CityVO> readCities(ReadCitiesSetEvent request) 
	{
		Page<City> dbContent = cityRepository.findAll(request.getPageable());
		List<CityVO> content = dbContent.getContent().stream().map(p -> 
		new CityVO(p.getId(), p.getName(),p.getCode(),p.getState().getId(),p.getState().getName(),p.getDistricts().getId(),p.getDistricts().getName())).collect(Collectors.toList());
		Page<CityVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
		return new PageReadEvent<>(page);
	}
}
