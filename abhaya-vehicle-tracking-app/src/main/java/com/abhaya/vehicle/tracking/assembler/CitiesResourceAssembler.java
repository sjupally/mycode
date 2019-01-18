package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.CitiesQueryController;
import com.abhaya.vehicle.tracking.resource.CitiesResource;
import com.abhaya.vehicle.tracking.vos.CityVO;

@Component
public class CitiesResourceAssembler extends ResourceAssemblerSupport<CityVO, CitiesResource> {

	 public CitiesResourceAssembler() 
	    {
			super(CitiesQueryController.class, CitiesResource.class);
		}

	@Override
	public CitiesResource toResource(CityVO entity)
	{
		return CitiesResource.builder()
				.cityId(entity.getId())
				.name(entity.getName())
				.code(entity.getCode())
				.districtId(entity.getDistrictId())
				.districtName(entity.getDistrictName())
				.stateId(entity.getStateId())
				.stateName(entity.getStateName())
				.build();
	}
	
	public CityVO fromResource(CitiesResource resource)
	{
		return CityVO.builder()
				.id(resource.getCityId())
				.name(resource.getName())
				.code(resource.getCode())
				.districtId(resource.getDistrictId())
				.districtName(resource.getDistrictName())
				.stateId(resource.getStateId())
				.stateName(resource.getStateName())
				.build();
	}
}
