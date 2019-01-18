package com.abhaya.vehicle.tracking.command.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abhaya.vehicle.tracking.assembler.DriverDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateDriverDetailsDataEvent;
import com.abhaya.vehicle.tracking.resource.DriverDetailsResource;
import com.abhaya.vehicle.tracking.services.DriverDetailsService;
import com.abhaya.vehicle.tracking.vos.DriverDetailsVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("v1")
public class DriverDetailsCommandController 
{
	@Autowired private DriverDetailsService service;
	@Autowired private DriverDetailsResourceAssembler assembler;

	@ApiOperation(value = "Create Driver", response = ResponseVO.class)
	@RequestMapping(value = "createDriver",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody DriverDetailsResource resource,HttpServletRequest request) throws IOException
	{
		log.info("---- START ---- REQUEST::" + resource);
		DriverDetailsVO driverDetailsVO = assembler.fromResource(resource);
		CreateDriverDetailsDataEvent event = new CreateDriverDetailsDataEvent();
		event.setDriverDetailsVO(driverDetailsVO);
		return service.save(event);
	}
	@ApiOperation(value = "Map RF Card with Driver ", response = ResponseVO.class)
	@RequestMapping(value = "mapRFCard",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO mapRFCard(@RequestBody DriverDetailsResource resource) throws IOException
	{
		log.info("---- START ---- REQUEST::" + resource);
		DriverDetailsVO driverDetailsVO = assembler.fromResource(resource);
		CreateDriverDetailsDataEvent event = new CreateDriverDetailsDataEvent();
		event.setDriverDetailsVO(driverDetailsVO);
		return service.mapRFCard(event);
	}
	
	@RequestMapping(value = "driver/addPhoto/{driverId}",method = RequestMethod.POST)
	public ResponseVO driver(@PathVariable("driverId") Long driverId, @RequestParam("file") MultipartFile file) throws IOException
	{
		log.info("---- START ---- REQUEST::" + driverId);
		log.info("File Name: " + file.getOriginalFilename());
		log.info("File Content Type: " + file.getContentType());
        return service.addPhoto(driverId, file);
	}
}
