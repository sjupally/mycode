package com.abhaya.vehicle.tracking.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.StateResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateStateEvent;
import com.abhaya.vehicle.tracking.resource.StateResource;
import com.abhaya.vehicle.tracking.services.StateService;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.StateVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class StateCommandController {

	@Autowired private StateService service;
	@Autowired private StateResourceAssembler assembler;
	
	@ApiOperation(value = "Create State", response = ResponseVO.class)
	@RequestMapping(value = "createState",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody StateResource resource,HttpServletRequest request)
	{
		StateVO stateVO = assembler.fromResource(resource);
		CreateStateEvent event = new CreateStateEvent();
		event.setStateVO(stateVO);
		return service.save(event);
	}
}
