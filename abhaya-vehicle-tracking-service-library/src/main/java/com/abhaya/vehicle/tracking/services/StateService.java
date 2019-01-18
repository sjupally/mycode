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
import com.abhaya.vehicle.tracking.data.model.State;
import com.abhaya.vehicle.tracking.data.repos.StateRepository;
import com.abhaya.vehicle.tracking.events.CreateStateEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadStateSetEvent;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.StateVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StateService implements ComboComponent
{
	@Autowired private StateRepository repository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		//List<State> dbContent = repository.getByStateId(Long.parseLong(extraParams[0]));
		List<State> dbContent = repository.findAll();
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), p.getCode())).collect(Collectors.toList());
	}
	
	
	public ResponseVO save(CreateStateEvent event) 
	{
		ResponseVO responseVO = new ResponseVO();
		try
		{
			StateVO stateVO = event.getStateVO();
			State state = null;
			ModelMapper modelMapper = new ModelMapper();
			if (!StringUtils.isEmpty(stateVO.getId()))
			{
				state = repository.getOne(stateVO.getId());
			}
			else
			{
				state = new State();
			}
			state = modelMapper.map(stateVO, State.class);
			repository.save(state);
			responseVO.setCode(Constants.ResponseMessages.CODE_200);
			responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
		}
		catch (Exception e)
		{
			responseVO.setCode(Constants.ResponseMessages.CODE_500);
			responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			log.info("Exception while saving State :: " + e.getCause() ,e);
		}
		return responseVO;
	} 
	
	public PageReadEvent<StateVO> readStates(ReadStateSetEvent request) 
	{
		Page<State> dbContent = repository.findAll(request.getPageable());
		List<StateVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), StateVO.class);
		Page<StateVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
		return new PageReadEvent<>(page);
	}
	
}