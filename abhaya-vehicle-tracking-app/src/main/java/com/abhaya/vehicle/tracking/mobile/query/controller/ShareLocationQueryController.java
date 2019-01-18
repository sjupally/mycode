package com.abhaya.vehicle.tracking.mobile.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.kafka.producers.ShareLocationProducer;
import com.abhaya.vehicle.tracking.kafka.producers.ShareRouteProducer;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mobile")
public class ShareLocationQueryController 
{
	@Autowired private ShareLocationProducer shareLocationProducer;
	@Autowired private ShareRouteProducer shareRouteProducer;

	@ApiOperation(value = "Share Location", response = ResponseEntity.class)
	@RequestMapping(value="shareLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> shareLocation(@RequestParam(value = "citizenMobileNumber", required = true) String citizenContactNumber)
	{
		shareLocationProducer.send(citizenContactNumber);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Share Route", response = ResponseEntity.class)
	@RequestMapping(value="shareRoute", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> shareRoute(@RequestParam(value = "citizenMobileNumber", required = true) String citizenContactNumber)
	{
		shareRouteProducer.send(citizenContactNumber);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
