package com.abhaya.vehicle.tracking.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.DistrictsResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateDistrictsEvent;
import com.abhaya.vehicle.tracking.resource.DistrictsResource;
import com.abhaya.vehicle.tracking.services.DistrictService;
import com.abhaya.vehicle.tracking.vos.DistrictsVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "v1")
public class DistrictsCommandController 
{

	@Autowired private DistrictService service;
	@Autowired private DistrictsResourceAssembler assembler;

	@ApiOperation(value = "Create District", response = ResponseVO.class)
	@RequestMapping(value = "createDistrict", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody DistrictsResource resource, HttpServletRequest request) 
	{
		DistrictsVO districtsVO = assembler.fromResource(resource);
		CreateDistrictsEvent event = new CreateDistrictsEvent();
		event.setDistrictsVO(districtsVO);
		return service.save(event);
	}
}
