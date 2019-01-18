package com.abhaya.vehicle.tracking.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.CommandExecutionResourceAssembler;
import com.abhaya.vehicle.tracking.kafka.producers.CommandExecutionProducer;
import com.abhaya.vehicle.tracking.resource.CommandExecutionResource;
import com.abhaya.vehicle.tracking.vos.CommandExecutionVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/executeCommand")
public class CommandExecutionController 
{
	@Autowired private CommandExecutionProducer producer ;
	@Autowired private CommandExecutionResourceAssembler assembler;
	
	@ApiOperation(value = "Command Execution")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void executeCommand(@RequestBody CommandExecutionResource resource, HttpServletRequest request) throws JsonProcessingException 
	{
		CommandExecutionVO executionVO = assembler.fromResource(resource);
		producer.send(new ObjectMapper().writeValueAsString(executionVO));
	}
}
