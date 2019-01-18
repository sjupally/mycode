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
import com.abhaya.vehicle.tracking.data.model.Districts;
import com.abhaya.vehicle.tracking.data.repos.DistrictsRepository;
import com.abhaya.vehicle.tracking.data.repos.StateRepository;
import com.abhaya.vehicle.tracking.events.CreateDistrictsEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDistrictsSetEvent;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;
import com.abhaya.vehicle.tracking.vos.DistrictsVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DistrictService implements ComboComponent
{
	@Autowired private DistrictsRepository repository;
	@Autowired private StateRepository stateRepository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<Districts> dbContent = repository.findByStateId(Long.parseLong(extraParams[0]));
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), p.getDescription())).collect(Collectors.toList());
	}
	
	public ResponseVO save(CreateDistrictsEvent event) 
	{
		ResponseVO responseVO = new ResponseVO();
		try
		{
			DistrictsVO districtsVO = event.getDistrictsVO();
			Districts districts = null;
			ModelMapper modelMapper = new ModelMapper();
			if (!StringUtils.isEmpty(districtsVO.getId()))
			{
				districts = repository.getOne(districtsVO.getId());
			}
			else
			{
				districts = new Districts();
			}
			districts = modelMapper.map(districtsVO, Districts.class);
			districts.setState(stateRepository.getOne(districtsVO.getStateId()));
			repository.save(districts);

			responseVO.setCode(Constants.ResponseMessages.CODE_200);
			responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
		}
		catch (Exception e)
		{
			responseVO.setCode(Constants.ResponseMessages.CODE_500);
			responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			log.info("Exception while saving Districts :: " + e.getCause() ,e);
		}
		return responseVO;
	} 
	
	public PageReadEvent<DistrictsVO> readDistricts(ReadDistrictsSetEvent request) 
	{
		Page<Districts> dbContent = repository.findAll(request.getPageable());
		List<DistrictsVO> content = dbContent.getContent().stream().map(p -> 
		new DistrictsVO(p.getId(), p.getName(),p.getDescription(),p.getState().getId(),p.getState().getName())).collect(Collectors.toList());
		Page<DistrictsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
		return new PageReadEvent<>(page);
	}
}
