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
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.TripDetailsViewResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadTripDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.TripDetailsViewResource;
import com.abhaya.vehicle.tracking.services.TripDetailsService;
import com.abhaya.vehicle.tracking.vos.TripDetailsViewVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/tripHistory")
public class TripHistoryQueryController 
{
	@Autowired private TripDetailsService service;
	@Autowired private TripDetailsViewResourceAssembler assembler;

	@ApiOperation(value = "View Trip Details History", response = ResponseEntity.class)
	@RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripDetailsViewResource>> getTripDetails(ReadTripDetailsSetEvent request,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<TripDetailsViewVO> pagedAssembler) 
	{
		request.setPageable(pageable);

		PageReadEvent<TripDetailsViewVO> event = service.readTripDetails(request);
		Page<TripDetailsViewVO> page = event.getPage();
		PagedResources<TripDetailsViewResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}