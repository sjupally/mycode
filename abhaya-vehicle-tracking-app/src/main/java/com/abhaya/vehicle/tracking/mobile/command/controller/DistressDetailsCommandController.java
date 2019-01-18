package com.abhaya.vehicle.tracking.mobile.command.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.kafka.producers.DistressAlertProducer;
import com.abhaya.vehicle.tracking.services.DistressDetailsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mobile/distress")
public class DistressDetailsCommandController 
{
	@Autowired private DistressAlertProducer distressAlertProducer ;
	@Autowired private DistressDetailsService distressDetailsService;

	@ApiOperation(value = "Create Distress Alert", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestParam(value = "citizenMobileNumber" ,required = true) String citizenMobileNumber,
			@RequestParam(value = "eventType" ,required = true) String eventType,
			HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		distressAlertProducer.send(String.join(",", citizenMobileNumber,eventType, "false"));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Create Distress Alert", response = ResponseEntity.class)
	@RequestMapping(value= "updateDistress" ,method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestParam(value = "citizenMobileNumber" ,required = true) String citizenMobileNumber,
			@RequestParam(value = "eventType" ,required = true) String eventType,
			HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//distressDetailsService.update(citizenMobileNumber);
		distressAlertProducer.send(String.join(",", citizenMobileNumber,eventType, "true"));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
