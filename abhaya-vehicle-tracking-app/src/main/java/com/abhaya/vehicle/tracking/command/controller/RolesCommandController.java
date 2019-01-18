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

import com.abhaya.vehicle.tracking.assembler.RolesResourceAssembler;
import com.abhaya.vehicle.tracking.resource.RolesResource;
import com.abhaya.vehicle.tracking.services.RolesService;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.RolesVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "v1/role")
public class RolesCommandController 
{

	@Autowired private RolesService service;
	@Autowired private RolesResourceAssembler assembler;

	@ApiOperation(value = "Create Role", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> save(@RequestBody RolesResource resource, HttpServletRequest request) 
	{
		RolesVO rolesVO = assembler.fromResource(resource);
		if(service.save(rolesVO))
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		else
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
