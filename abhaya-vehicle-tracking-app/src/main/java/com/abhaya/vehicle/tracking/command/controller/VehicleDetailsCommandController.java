package com.abhaya.vehicle.tracking.command.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.VehicleDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateVehicleDetailsEvent;
import com.abhaya.vehicle.tracking.resource.VehicleDetailsResource;
import com.abhaya.vehicle.tracking.services.VehicleDetailsService;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.VehicleDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class VehicleDetailsCommandController 
{

	@Autowired private VehicleDetailsService service;
	@Autowired private VehicleDetailsResourceAssembler  assembler;

	@ApiOperation(value = "Create Vehicle", response = ResponseVO.class)
	@RequestMapping(value = "createVehicle",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody VehicleDetailsResource resource,HttpServletRequest request)
	{
		log.info("---- START ---- REQUEST::"+ resource);
		VehicleDetailsVO vehicleDetailsVO = assembler.fromResource(resource);
		CreateVehicleDetailsEvent event = new CreateVehicleDetailsEvent();
		event.setVehicleDetailsVO(vehicleDetailsVO);
		return service.save(event);
	}

	@ApiOperation(value = "Map IoT Device with  Vehicle", response = ResponseVO.class)
	@RequestMapping(value = "mapIoTDevice",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO mapIoTDevice(@RequestBody VehicleDetailsResource resource)
	{
		log.info("---- START ---- REQUEST:: Vehicle Number"+ resource.getRcNumber() +"  Serial Number "+ resource.getSerialNumber());
		return service.mapIoTDevice(resource.getRcNumber(),resource.getSerialNumber());
	}
}
