package com.abhaya.vehicle.tracking.query.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.StateResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadStateSetEvent;
import com.abhaya.vehicle.tracking.resource.StateResource;
import com.abhaya.vehicle.tracking.services.StateService;
import com.abhaya.vehicle.tracking.vos.StateVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class StateQueryController {

	@Autowired private StateService service;
	@Autowired private StateResourceAssembler assembler;
	
	@ApiOperation(value = "View list of States", response = ResponseEntity.class)
	@RequestMapping(value="viewStates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<StateResource>> readStatesData(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<StateVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadStateSetEvent request = new ReadStateSetEvent();
		request.setPageable(pageable);
		PageReadEvent<StateVO> event = service.readStates(request);
		Page<StateVO> page = event.getPage();
		PagedResources<StateResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
