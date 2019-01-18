package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.StateQueryController;
import com.abhaya.vehicle.tracking.resource.StateResource;
import com.abhaya.vehicle.tracking.vos.StateVO;

@Component
public class StateResourceAssembler extends ResourceAssemblerSupport<StateVO, StateResource> {

	 public StateResourceAssembler() 
	    {
			super(StateQueryController.class, StateResource.class);
		}

	@Override
	public StateResource toResource(StateVO entity)
	{
		return StateResource.builder()
				.stateId(entity.getId())
				.name(entity.getName())
				.code(entity.getCode())
				.build();
	}
	
	public StateVO fromResource(StateResource resource)
	{
		return StateVO.builder()
				.id(resource.getStateId())
				.name(resource.getName())
				.code(resource.getCode())
				.build();
	}
}
