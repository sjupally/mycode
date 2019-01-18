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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.TripDetailsViewResourceAssembler;
import com.abhaya.vehicle.tracking.events.EntityReadEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadTripDetailsSetEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDataEvent;
import com.abhaya.vehicle.tracking.resource.TripDetailsViewResource;
import com.abhaya.vehicle.tracking.services.TripDetailsService;
import com.abhaya.vehicle.tracking.vos.TripDetailsViewVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/tripDetails")
public class TripDetailsViewQueryController 
{
	@Autowired private TripDetailsService service;
	@Autowired private TripDetailsViewResourceAssembler assembler;

	@ApiOperation(value = "View list of Trip Details", response = ResponseEntity.class)
	@RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripDetailsViewResource>> getTripDetails(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<TripDetailsViewVO> pagedAssembler,@RequestParam(value = "cityId" , required = false) Long cityId,@RequestParam(value = "stateId" , required = false) Long stateId,
			@RequestParam(value = "districtId" , required = false) Long districtId,@RequestParam(value = "searchValue" , required = false) String searchValue,
			@RequestParam(value = "startDate" , required = false) String startDate,@RequestParam(value = "isTripClosed" , required = false) Boolean isTripClosed) 
	{
		ReadTripDetailsSetEvent request = new ReadTripDetailsSetEvent();
		request.setPageable(pageable);
		request.setSearchValue(searchValue);
		request.setIsTripClosed(isTripClosed);
		request.setStartDate(startDate);
		request.setCityId(cityId);
		request.setStateId(stateId);
		request.setDistrictId(districtId);

		PageReadEvent<TripDetailsViewVO> event = service.readTripDetails(request);
		Page<TripDetailsViewVO> page = event.getPage();
		PagedResources<TripDetailsViewResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View Single Trip", response = ResponseEntity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TripDetailsViewResource> readDataById(@PathVariable Long id) 
	{
		ReadVehicleDataEvent request = new ReadVehicleDataEvent().setId(id);
		EntityReadEvent<TripDetailsViewVO> event = service.readDataById(request);
		if (!event.isFound()) 
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		TripDetailsViewVO entity = event.getEntity();
		return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
	}
}
