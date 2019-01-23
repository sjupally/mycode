package com.abhaya.vehicle.tracking.mobile.query.controller;

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

import com.abhaya.vehicle.tracking.assembler.EmergencyContactsResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadEmergencyContactsSetEvent;
import com.abhaya.vehicle.tracking.resource.EmergencyContactsResource;
import com.abhaya.vehicle.tracking.services.EmergencyContactsService;
import com.abhaya.vehicle.tracking.vos.EmergencyContactNumbersVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mobile")
public class EmergencyContactsMobileQueryController 
{
	
	@Autowired private EmergencyContactsService service;
	@Autowired private EmergencyContactsResourceAssembler assembler;

	@ApiOperation(value = "View list of Emergency Contact Details based on Citizen Mobile Number for Mobile", response = ResponseEntity.class)
	@RequestMapping(value="emergencyContacts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<EmergencyContactsResource>> readData(@RequestParam(value = "citizenMobileNumber" , required = true) String citizenMobileNumber,
			@RequestParam(value = "emergencyContactNumber" , required = false) String emergencyContactNumber,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<EmergencyContactNumbersVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadEmergencyContactsSetEvent request = new ReadEmergencyContactsSetEvent();
		request.setPageable(pageable);
		request.setCitizenMobileNumber(citizenMobileNumber);
		request.setEmergencyContactNumber(emergencyContactNumber);

		PageReadEvent<EmergencyContactNumbersVO> event = service.readData(request);
		Page<EmergencyContactNumbersVO> page = event.getPage();
		PagedResources<EmergencyContactsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
