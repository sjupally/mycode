package com.abhaya.vehicle.tracking.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.WatchVehicleResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateWatchVehicleEvent;
import com.abhaya.vehicle.tracking.resource.WatchVehicleResource;
import com.abhaya.vehicle.tracking.services.WatchVehicleService;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.WatchVehicleVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "v1/watchVehicle")
public class WatchVehicleCommandController 
{

	@Autowired private WatchVehicleService service;
	@Autowired private WatchVehicleResourceAssembler  assembler;

	@ApiOperation(value = "Create Watch Vehicle with RC Number Only", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody WatchVehicleResource resource,HttpServletRequest request)
	{
		WatchVehicleVO vehicleDetailsVO = assembler.fromResource(resource);
		CreateWatchVehicleEvent event = new CreateWatchVehicleEvent();
		event.setWatchVehicleVO(vehicleDetailsVO);
		return service.save(event);
	}
}

