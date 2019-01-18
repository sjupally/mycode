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

import com.abhaya.vehicle.tracking.assembler.ModemDeviceResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadModemDataSetEvent;
import com.abhaya.vehicle.tracking.resource.ModemDeviceResource;
import com.abhaya.vehicle.tracking.services.ModemDetailsService;
import com.abhaya.vehicle.tracking.vos.ModemDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/modemDetails")
public class ModemDeviceController 
{
	@Autowired private ModemDetailsService service;
	@Autowired private ModemDeviceResourceAssembler assembler;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "View list of Modem Details", response = ResponseEntity.class)
	public ResponseEntity<PagedResources<ModemDeviceResource>> readModemDeviceData(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<ModemDetailsVO> pagedAssembler,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchDate", required = false) String searchDate)
	{
		ReadModemDataSetEvent request = new ReadModemDataSetEvent();
		request.setSearchValue(searchValue);
		request.setSearchDate(searchDate);
		request.setPageable(pageable);
		PageReadEvent<ModemDetailsVO> event = service.readModemDeviceData(request);
		Page<ModemDetailsVO> page = event.getPage();
		PagedResources<ModemDeviceResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
