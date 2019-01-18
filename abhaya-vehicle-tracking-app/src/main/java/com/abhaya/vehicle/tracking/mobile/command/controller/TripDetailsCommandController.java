package com.abhaya.vehicle.tracking.mobile.command.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.TripDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.kafka.producers.EndTripProducer;
import com.abhaya.vehicle.tracking.resource.TripDetailsResource;
import com.abhaya.vehicle.tracking.services.TripDetailsService;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.TripVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value= "mobile")
public class TripDetailsCommandController 
{

	@Autowired private TripDetailsResourceAssembler  assembler;
	@Autowired private TripDetailsService service;
	@Autowired private EndTripProducer endTripProducer ;
	

	@ApiOperation(value = "Initiating Trip", response = ResponseVO.class)
	@RequestMapping(value= "initiateTrip",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@Valid @RequestBody TripDetailsResource tripDetailsResource,HttpServletRequest request) throws JsonProcessingException
	{
		TripVO tripDetailsVO = assembler.fromResource(tripDetailsResource);
		return service.save(tripDetailsVO);

		/*intiationProducer.send(new ObjectMapper().writeValueAsString(tripDetailsVO));

		ResponseVO responseVO = new ResponseVO();
		responseVO.setCode(Constants.ResponseMessages.CODE_200);
		responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);

		return responseVO;*/
	}

	@ApiOperation(value = "End Trip", response = ResponseEntity.class)
	@RequestMapping(value= "endTrip",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> endTrip(@RequestParam("vehicleNumber") String vehicleNumber , HttpServletRequest request)
	{
		endTripProducer.send(vehicleNumber);
		if(service.endTrip(vehicleNumber)) return new ResponseEntity<>(HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
}
