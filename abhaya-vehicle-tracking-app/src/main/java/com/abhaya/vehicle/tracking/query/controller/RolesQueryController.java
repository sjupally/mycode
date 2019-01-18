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

import com.abhaya.vehicle.tracking.assembler.RolesResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadCitiesSetEvent;
import com.abhaya.vehicle.tracking.resource.RolesResource;
import com.abhaya.vehicle.tracking.services.RolesService;
import com.abhaya.vehicle.tracking.vos.RolesVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/role")
public class RolesQueryController {

	@Autowired private RolesService service;
	@Autowired private RolesResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Roles", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<RolesResource>> readCitiesData(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<RolesVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadCitiesSetEvent request = new ReadCitiesSetEvent();
		request.setPageable(pageable);
		PageReadEvent<RolesVO> event = service.readData(request);
		Page<RolesVO> page = event.getPage();
		PagedResources<RolesResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}