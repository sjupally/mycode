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

import com.abhaya.vehicle.tracking.assembler.DriverDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.DriverDetailsResource;
import com.abhaya.vehicle.tracking.services.DriverDetailsService;
import com.abhaya.vehicle.tracking.vos.DriverDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/driverDetails")
@Slf4j
public class DriverDetailsQueryController 
{
	@Autowired private DriverDetailsService service;
	@Autowired private DriverDetailsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Drivers", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<DriverDetailsResource>> readUsersData(
			@RequestParam(value = "driverName", required = false) String driverName,
			@RequestParam(value = "dlNumber", required = false) String dlNumber,
			@RequestParam(value = "rfId", required = false) String rfId,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "districtId", required = false) Long districtId,
			@RequestParam(value = "cityId", required = false) Long cityId,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "driverContactNumber" , required = false) String driverContactNumber,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<DriverDetailsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadDriverDetailsSetEvent request = new ReadDriverDetailsSetEvent();
		request.setDriverName(driverName);
		request.setDlNumber(dlNumber);
		request.setRfId(rfId);
		request.setSearchValue(searchValue);
		request.setDistrictId(districtId);
		request.setCityId(cityId);
		request.setDate(date);
		request.setPageable(pageable);
		request.setDriverContactNumber(driverContactNumber);

		PageReadEvent<DriverDetailsVO> event = service.readDriverData(request);
		Page<DriverDetailsVO> page = event.getPage();
		PagedResources<DriverDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
