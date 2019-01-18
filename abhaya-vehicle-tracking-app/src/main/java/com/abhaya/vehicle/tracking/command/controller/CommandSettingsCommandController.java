package com.abhaya.vehicle.tracking.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.CommandSettingsResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateCommandSettingsEvent;
import com.abhaya.vehicle.tracking.resource.CommandSettingsResource;
import com.abhaya.vehicle.tracking.services.CommandSettingsService;
import com.abhaya.vehicle.tracking.vos.CommandSettingsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/commandSettings")
public class CommandSettingsCommandController 
{
	
	@Autowired private CommandSettingsService service;
	@Autowired private CommandSettingsResourceAssembler assembler;
	
	@ApiOperation(value = "Create Setting", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody CommandSettingsResource resource,HttpServletRequest request)
	{
		CommandSettingsVO settingsVO = assembler.fromResource(resource);
		CreateCommandSettingsEvent event = new CreateCommandSettingsEvent();
		event.setCommandSettingsVO(settingsVO);
		service.save(event);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
