package com.abhaya.vehicle.tracking.mobile.command.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.kafka.producers.DistressVideoAlertProducer;
import com.abhaya.vehicle.tracking.services.DistressDetailsService;
import com.abhaya.vehicle.tracking.services.DistressDetailsService.impl;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("mobile/video")
public class DistressVideoCommandController 
{
	@Autowired private DistressVideoAlertProducer distressVideoAlertProducer ;
	@Autowired private DistressDetailsService distressDetailsService;
	@Autowired private SimpMessagingTemplate template;

	@ApiOperation(value = "Create Distress Video Alert", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestParam(value = "citizenMobileNumber" ,required = true) String citizenMobileNumber,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		log.info("citizenMobileNumber " + citizenMobileNumber);
		template.convertAndSend("/topic/messages", "A Distress Video alert is raised from Passenger with Mobile Number : " + citizenMobileNumber);
		//distressVideoAlertProducer.send(citizenMobileNumber);
		//distressDetailsService.save(citizenMobileNumber);
				
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
