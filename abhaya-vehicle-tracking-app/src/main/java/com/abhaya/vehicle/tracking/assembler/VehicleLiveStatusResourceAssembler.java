package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.TripTrackingQueryController;
import com.abhaya.vehicle.tracking.resource.VehicleLiveStatusResource;
import com.abhaya.vehicle.tracking.utils.VehicleDashboardVO;

@Component
public class VehicleLiveStatusResourceAssembler extends ResourceAssemblerSupport<VehicleDashboardVO, VehicleLiveStatusResource> 
{
    public VehicleLiveStatusResourceAssembler() 
    {
		super(TripTrackingQueryController.class, VehicleLiveStatusResource.class);
	}

    @Override
    public VehicleLiveStatusResource toResource(VehicleDashboardVO event) 
    {
    	return VehicleLiveStatusResource.builder()
				.rcNumber(event.getRcNumber())
				.serialNumber(event.getSerialNumber())
				.trackId(event.getId())
				.langitude(event.getLangitude())
				.latitude(event.getLatitude())
				.build();
    }
}
