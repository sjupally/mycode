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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.WatchVehicleResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadWatchVehicleDataSetEvent;
import com.abhaya.vehicle.tracking.resource.WatchVehicleResource;
import com.abhaya.vehicle.tracking.services.WatchVehicleService;
import com.abhaya.vehicle.tracking.vos.WatchVehicleVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/watchVehicle")
public class WatchVehicleQueryController 
{
	@Autowired private WatchVehicleService service;
	@Autowired private WatchVehicleResourceAssembler assembler;

	@ApiOperation(value = "View list of Watch Vehicles", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<WatchVehicleResource>> readVehicleData(
			@RequestParam(value = "rcNumber", required = false) String rcNumber,
			@RequestParam(value = "serialNumber", required = false) String serialNumber,
			@RequestParam(value = "stateId", required = false) Long stateId,
			@RequestParam(value = "districtId", required = false) Long districtId,
			@RequestParam(value = "cityId", required = false) String cityId,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<WatchVehicleVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadWatchVehicleDataSetEvent request = new ReadWatchVehicleDataSetEvent();
		request.setPageable(pageable);
		request.setRcNumber(rcNumber);
		request.setSerialNumber(serialNumber);
		request.setDistrictId(districtId);
		request.setStateId(stateId);
		request.setCityId(request.getCityId());
		
		PageReadEvent<WatchVehicleVO> event = service.readData(request);
		Page<WatchVehicleVO> page = event.getPage();
		PagedResources<WatchVehicleResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}