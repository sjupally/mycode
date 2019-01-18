package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.PoliceStationDetailsQueryController;
import com.abhaya.vehicle.tracking.resource.PoliceStationDetailsResource;
import com.abhaya.vehicle.tracking.vos.PoliceStationDetailsVO;

@Component
public class PoliceStationDetailsResourceAssembler extends ResourceAssemblerSupport<PoliceStationDetailsVO, PoliceStationDetailsResource> 
{
    public PoliceStationDetailsResourceAssembler() 
    {
		super(PoliceStationDetailsQueryController.class, PoliceStationDetailsResource.class);
	}

    @Override
    public PoliceStationDetailsResource toResource(PoliceStationDetailsVO entity) 
    {
    	return PoliceStationDetailsResource.builder()
    		.address(entity.getAddress())
    		.city(entity.getCity())
    		.contactNumber(entity.getContactNumber())
    		.division(entity.getDivision())
    		.emailId(entity.getEmailId())
    		.jurisdiction(entity.getJurisdiction())
    		.langitude(entity.getLangitude())
    		.latitude(entity.getLatitude())
    		.mobileNumber(entity.getMobileNumber())
    		.pincode(entity.getPincode())
    		.stationId(entity.getId())
    		.stationName(entity.getStationName())
			.build();
    }
}