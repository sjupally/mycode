package com.abhaya.vehicle.tracking.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.CitiesResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateCitiesEvent;
import com.abhaya.vehicle.tracking.resource.CitiesResource;
import com.abhaya.vehicle.tracking.services.CityService;
import com.abhaya.vehicle.tracking.vos.CityVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class CitiesCommandController {

	@Autowired private CityService service;
	@Autowired private CitiesResourceAssembler assembler;

	@ApiOperation(value = "Create City", response = ResponseVO.class)
	@RequestMapping(value = "createCity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody CitiesResource resource, HttpServletRequest request) {
		CityVO cityVO = assembler.fromResource(resource);
		CreateCitiesEvent event = new CreateCitiesEvent();
		event.setCityVO(cityVO);
		return service.save(event);
	}
}
