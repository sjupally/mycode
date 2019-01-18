package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.DistrictsQueryController;
import com.abhaya.vehicle.tracking.resource.DistrictsResource;
import com.abhaya.vehicle.tracking.vos.DistrictsVO;

@Component
public class DistrictsResourceAssembler extends ResourceAssemblerSupport<DistrictsVO, DistrictsResource> {

	 public DistrictsResourceAssembler() 
	    {
			super(DistrictsQueryController.class, DistrictsResource.class);
		}

	@Override
	public DistrictsResource toResource(DistrictsVO entity)
	{
		return DistrictsResource.builder()
				.districtId(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.stateId(entity.getStateId())
				.stateName(entity.getStateName())
				.build();
	}
	
	public DistrictsVO fromResource(DistrictsResource resource)
	{
		return DistrictsVO.builder()
				.id(resource.getDistrictId())
				.name(resource.getName())
				.description(resource.getDescription())
				.stateId(resource.getStateId())
				.stateName(resource.getStateName())
				.build();
	}
}
