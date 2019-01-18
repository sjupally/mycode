package com.abhaya.vehicle.tracking.mobile.command.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.EmergencyContactsResourceAssembler;
import com.abhaya.vehicle.tracking.events.CreateEmergencyContactsDataEvent;
import com.abhaya.vehicle.tracking.resource.EmergencyContactsResource;
import com.abhaya.vehicle.tracking.services.EmergencyContactsService;
import com.abhaya.vehicle.tracking.vos.EmergencyContactNumbersVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mobile")
public class EmergencyContactsCommandController 
{
	@Autowired private EmergencyContactsService service;
	@Autowired private EmergencyContactsResourceAssembler assembler;

	@ApiOperation(value = "Create Emergency Contacts for Citizen  using Citizen Mobile Number", response = ResponseVO.class)
	@RequestMapping(value = "emergencyContacts",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody List<EmergencyContactsResource> resources,HttpServletRequest request)
	{
		List<EmergencyContactNumbersVO> numbersVOs = assembler.fromResource(resources);
		CreateEmergencyContactsDataEvent event = new CreateEmergencyContactsDataEvent();
		event.setEmergencyContactNumbersVOs(numbersVOs);
		return service.save(event);
	}
	
	@ApiOperation(value = "Delete Emergency Contacts for Citizen  using Citizen Mobile Number", response = ResponseVO.class)
	@RequestMapping(value = "deleteEmgContacts",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEmgContacts(@RequestParam(value = "citizenMobileNumber" ,required = true) String citizenMobileNumber,@RequestParam(value = "emergencyContactNumber" ,required = true) String emergencyContactNumber)
	{
		service.deleteEmgContacts(citizenMobileNumber,emergencyContactNumber);
	}
}
