package com.abhaya.vehicle.tracking.mobile.command.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.RouteDeviationResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateRouteDeviationEvent;
import com.abhaya.vehicle.tracking.kafka.producers.RouteDeviationProducer;
import com.abhaya.vehicle.tracking.resource.RouteDeviationResource;
import com.abhaya.vehicle.tracking.services.RouteDeviationService;
import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("mobile/routeDeviation")
public class RouteDeviationCommandController 
{
	
	@Autowired private RouteDeviationResourceAssembler assembler;
	@Autowired private RouteDeviationProducer routeDeviationProducer;

	@ApiOperation(value = "Create Route Deviation Request")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void save(@Valid @RequestBody RouteDeviationResource resource,HttpServletRequest request) throws JsonProcessingException
	{
		log.info("Create Route Deviation Request " + resource);
		RouteDeviationVO routeDeviationVO = assembler.fromResource(resource);

		routeDeviationProducer.send(String.join("#", routeDeviationVO.getLocation(),routeDeviationVO.getCitizenMobileNumber()));
		
	}
}