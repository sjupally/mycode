package com.abhaya.vehicle.tracking.query.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.CommandSettingsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadCommandSettingsSetEvent;
import com.abhaya.vehicle.tracking.resource.CommandSettingsResource;
import com.abhaya.vehicle.tracking.services.CommandSettingsService;
import com.abhaya.vehicle.tracking.vos.CommandSettingsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/commandSettings")
public class CommandSettingsQueryController 
{
	
	@Autowired private CommandSettingsService service;
	@Autowired private CommandSettingsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Setting Commands", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<CommandSettingsResource>> readUsersData(@RequestParam(value = "commandName",required = false) String commandName,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<CommandSettingsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadCommandSettingsSetEvent request = new ReadCommandSettingsSetEvent();
		request.setPageable(pageable);
		request.setCommandName(commandName);

		PageReadEvent<CommandSettingsVO> event = service.readDate(request);
		Page<CommandSettingsVO> page = event.getPage();
		PagedResources<CommandSettingsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
