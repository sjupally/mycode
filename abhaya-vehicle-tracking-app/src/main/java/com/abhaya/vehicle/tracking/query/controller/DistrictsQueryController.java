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

import com.abhaya.vehicle.tracking.assembler.DistrictsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDistrictsSetEvent;
import com.abhaya.vehicle.tracking.resource.DistrictsResource;
import com.abhaya.vehicle.tracking.services.DistrictService;
import com.abhaya.vehicle.tracking.vos.DistrictsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class DistrictsQueryController 
{

	@Autowired private DistrictService service;
	@Autowired private DistrictsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Districts", response = ResponseEntity.class)
	@RequestMapping(value="viewDistricts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<DistrictsResource>> readDistrictsData(
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<DistrictsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadDistrictsSetEvent request = new ReadDistrictsSetEvent();
		request.setPageable(pageable);
		PageReadEvent<DistrictsVO> event = service.readDistricts(request);
		Page<DistrictsVO> page = event.getPage();
		PagedResources<DistrictsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
