package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.command.controller.RolesCommandController;
import com.abhaya.vehicle.tracking.resource.RolesResource;
import com.abhaya.vehicle.tracking.vos.RolesVO;

@Component
public class RolesResourceAssembler extends ResourceAssemblerSupport<RolesVO, RolesResource> {

	 public RolesResourceAssembler() 
	    {
			super(RolesCommandController.class, RolesResource.class);
		}

	@Override
	public RolesResource toResource(RolesVO entity)
	{
		return RolesResource.builder()
				.roleId(entity.getId())
				.name(entity.getName())
				.privileges(entity.getPrivileges())
				.build();
	}
	
	public RolesVO fromResource(RolesResource resource)
	{
		return RolesVO.builder()
				.id(resource.getRoleId())
				.name(resource.getName())
				.privilege(resource.getPrivilege())
				.build();
	}
}