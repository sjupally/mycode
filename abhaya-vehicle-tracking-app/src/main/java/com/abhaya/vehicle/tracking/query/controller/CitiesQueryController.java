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

import com.abhaya.vehicle.tracking.assembler.CitiesResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadCitiesSetEvent;
import com.abhaya.vehicle.tracking.resource.CitiesResource;
import com.abhaya.vehicle.tracking.services.CityService;
import com.abhaya.vehicle.tracking.vos.CityVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class CitiesQueryController {

	@Autowired private CityService service;
	@Autowired private CitiesResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Cities", response = ResponseEntity.class)
	@RequestMapping(value="viewCities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<CitiesResource>> readCitiesData(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<CityVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		
		ReadCitiesSetEvent request = new ReadCitiesSetEvent();
		request.setPageable(pageable);
		PageReadEvent<CityVO> event = service.readCities(request);
		Page<CityVO> page = event.getPage();
		PagedResources<CitiesResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
