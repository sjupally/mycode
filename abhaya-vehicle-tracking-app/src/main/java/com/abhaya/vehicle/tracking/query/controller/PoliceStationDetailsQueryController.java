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

import com.abhaya.vehicle.tracking.assembler.PoliceStationDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadPoliceStationDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.PoliceStationDetailsResource;
import com.abhaya.vehicle.tracking.services.PoliceStationDetailsService;
import com.abhaya.vehicle.tracking.vos.PoliceStationDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/policeStations")
public class PoliceStationDetailsQueryController 
{
	@Autowired private PoliceStationDetailsService service;
	@Autowired private PoliceStationDetailsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of PoliceStations", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<PoliceStationDetailsResource>> readUsersData(ReadPoliceStationDetailsSetEvent request,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<PoliceStationDetailsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		log.info("---- START ---- REQUEST::"+ request);
		request.setPageable(pageable);
		PageReadEvent<PoliceStationDetailsVO> event=service.readPoliceStationsData(request);
		Page<PoliceStationDetailsVO> page=event.getPage();
		PagedResources<PoliceStationDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}