package com.abhaya.vehicle.tracking.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.mobile.command.controller.EmergencyContactsCommandController;
import com.abhaya.vehicle.tracking.resource.EmergencyContactsResource;
import com.abhaya.vehicle.tracking.vos.EmergencyContactNumbersVO;

@Component
public class EmergencyContactsResourceAssembler extends ResourceAssemblerSupport<EmergencyContactNumbersVO, EmergencyContactsResource> 
{
	public EmergencyContactsResourceAssembler() 
	{
		super(EmergencyContactsCommandController.class, EmergencyContactsResource.class);
	}

	@Override
	public EmergencyContactsResource toResource(EmergencyContactNumbersVO entity) 
	{
		return EmergencyContactsResource.builder()
				.citizenMobileNumber(entity.getCitizenMobileNumber())
				.emergencyContactNumber(entity.getEmergencyContactNumber())
				.name(entity.getName())
			.build();
	}

	public List<EmergencyContactNumbersVO> fromResource(List<EmergencyContactsResource> resources) 
	{
		List<EmergencyContactNumbersVO> contactNumbersVOs = new ArrayList<>();
		for (EmergencyContactsResource resource : resources)
		{
			EmergencyContactNumbersVO numbersVO = 	EmergencyContactNumbersVO.builder()
					.citizenMobileNumber(resource.getCitizenMobileNumber())
					.emergencyContactNumber(resource.getEmergencyContactNumber())
					.name(resource.getName())
					.build();
			contactNumbersVOs.add(numbersVO);
		}
		return contactNumbersVOs;
	}
}