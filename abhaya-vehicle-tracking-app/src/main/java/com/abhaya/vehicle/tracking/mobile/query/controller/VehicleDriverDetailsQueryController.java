package com.abhaya.vehicle.tracking.mobile.query.controller;

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

import com.abhaya.vehicle.tracking.assembler.VehicleDriverDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDutyDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.VehicleDriverDetailsResource;
import com.abhaya.vehicle.tracking.services.VehicleDriverDetailsService;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mobile")
public class VehicleDriverDetailsQueryController 
{
	@Autowired private VehicleDriverDetailsService service;
	@Autowired private VehicleDriverDetailsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of vehicle and Driver Details for Mobile", response = ResponseEntity.class)
	@RequestMapping(value="vehicleDriverDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<VehicleDriverDetailsResource>> vehicleDriverDetails(@RequestParam(value = "vehicleNumber", required = true) String vehicleNumber,
			HttpServletRequest httpServletRequest,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<VehicleDriverDetailsVO> pagedAssembler) 
	{
		ReadDriverDutyDetailsSetEvent request = new ReadDriverDutyDetailsSetEvent();
		request.setRcNumber(vehicleNumber);
		request.setPageable(pageable);
		
		PageReadEvent<VehicleDriverDetailsVO> event = service.readVehicleDriverData(request);
		Page<VehicleDriverDetailsVO> page = event.getPage();
		PagedResources<VehicleDriverDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
