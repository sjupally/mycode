package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.command.controller.PrivilegesCommandController;
import com.abhaya.vehicle.tracking.resource.PrivilegesResource;
import com.abhaya.vehicle.tracking.vos.PrivilagesVO;

@Component
public class PrivilegesResourceAssembler extends ResourceAssemblerSupport<PrivilagesVO, PrivilegesResource> {

	 public PrivilegesResourceAssembler() 
	    {
			super(PrivilegesCommandController.class, PrivilegesResource.class);
		}

	@Override
	public PrivilegesResource toResource(PrivilagesVO entity)
	{
		return PrivilegesResource.builder()
				.privilegeId(entity.getId())
				.name(entity.getName())
				.build();
	}
	
	public PrivilagesVO fromResource(PrivilegesResource resource)
	{
		return PrivilagesVO.builder()
				.id(resource.getPrivilegeId())
				.name(resource.getName())
				.build();
	}
}
