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

import com.abhaya.vehicle.tracking.assembler.RawPacketDataResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadRawDataSetEvent;
import com.abhaya.vehicle.tracking.resource.RawPacketDataResource;
import com.abhaya.vehicle.tracking.services.RawDataPacketService;
import com.abhaya.vehicle.tracking.vos.RawDataPacketVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/rawData")
public class RawPacketDataQueryController 
{
	
	@Autowired private RawDataPacketService service;
	@Autowired private RawPacketDataResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "View list of Raw Packet Details", response = ResponseEntity.class)
	public ResponseEntity<PagedResources<RawPacketDataResource>> readModemDeviceData(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<RawDataPacketVO> pagedAssembler,
			@RequestParam(value = "searchValue", required = false) String searchValue,@RequestParam(value = "packetDate", required = false) String packetDate)
	{
		ReadRawDataSetEvent request = new ReadRawDataSetEvent();
		request.setSearchValue(searchValue);
		request.setPacketDate(packetDate);
		request.setPageable(pageable);
		PageReadEvent<RawDataPacketVO> event = service.readData(request);
		Page<RawDataPacketVO> page = event.getPage();
		PagedResources<RawPacketDataResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
