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

import com.abhaya.vehicle.tracking.assembler.PrivilegesResourceAssembler;
import com.abhaya.vehicle.tracking.resource.PrivilegesResource;
import com.abhaya.vehicle.tracking.services.PrivilegesService;
import com.abhaya.vehicle.tracking.vos.PrivilagesVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "v1/privilege")
public class PrivilegesCommandController 
{

	@Autowired private PrivilegesService service;
	@Autowired private PrivilegesResourceAssembler assembler;

	@ApiOperation(value = "Create Privileges", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> save(@RequestBody PrivilegesResource resource, HttpServletRequest request) 
	{
		PrivilagesVO rolesVO = assembler.fromResource(resource);
		if(service.save(rolesVO))
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		else
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
