package com.abhaya.vehicle.tracking.query.controller;

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

import com.abhaya.vehicle.tracking.assembler.DriverDutyDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDutyDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.DriverDutyDetailsResource;
import com.abhaya.vehicle.tracking.services.DriverDutyDetailsService;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/driverDutyDetails")
public class DriverDutyDetailsQueryController 
{
	@Autowired private DriverDutyDetailsService service;
	@Autowired private DriverDutyDetailsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Driver Duty Details", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<DriverDutyDetailsResource>> vehicleDriverDetails(
			@RequestParam(value = "vehicleNumber", required = false) String vehicleNumber,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "packetDate", required = false) String packetDate,
			@RequestParam(value = "districtId", required = false) Long districtId,
			@RequestParam(value = "cityId", required = false) Long cityId,
			@RequestParam(value = "date", required = false) String date,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<VehicleDriverDetailsVO> pagedAssembler) 
	{
		ReadDriverDutyDetailsSetEvent request = new ReadDriverDutyDetailsSetEvent();
		request.setRcNumber(vehicleNumber);
		request.setSearchValue(searchValue);
		request.setPacketDate(packetDate);
		request.setDistrictId(districtId);
		request.setCityId(cityId);
		request.setDate(date);
		request.setPageable(pageable);
		
		PageReadEvent<VehicleDriverDetailsVO> event = service.readData(request);
		Page<VehicleDriverDetailsVO> page = event.getPage();
		PagedResources<DriverDutyDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
