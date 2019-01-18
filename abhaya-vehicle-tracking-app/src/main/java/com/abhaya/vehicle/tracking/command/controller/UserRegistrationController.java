package com.abhaya.vehicle.tracking.command.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.UserDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateUserEvent;
import com.abhaya.vehicle.tracking.resource.UserDetailsResource;
import com.abhaya.vehicle.tracking.services.UserService;
import com.abhaya.vehicle.tracking.vos.ResponseVO;
import com.abhaya.vehicle.tracking.vos.UsersDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/userDetails")
public class UserRegistrationController 
{

	@Autowired private UserService service;
	@Autowired private UserDetailsResourceAssembler assembler;
	@Autowired private PasswordEncoder passwordEncoder;

	@ApiOperation(value = "User Registration", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@Valid @RequestBody UserDetailsResource userResource)
	{
		log.info("---- START ---- REQUEST::"+ userResource);
		UsersDetailsVO usersDTO = assembler.fromResource(userResource);
		CreateUserEvent  createUserEvent = new CreateUserEvent();
		if (usersDTO.getPassword() != null)
			usersDTO.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
		createUserEvent.setUsersDetailsVO(usersDTO);
		return service.save(createUserEvent);
	}
}