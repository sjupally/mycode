package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.mobile.command.controller.TripDetailsCommandController;
import com.abhaya.vehicle.tracking.resource.TripDetailsResource;
import com.abhaya.vehicle.tracking.vos.TripDetailsVO;
import com.abhaya.vehicle.tracking.vos.TripVO;

@Component
public class TripDetailsResourceAssembler extends ResourceAssemblerSupport<TripDetailsVO, TripDetailsResource> 
{
    public TripDetailsResourceAssembler() 
    {
		super(TripDetailsCommandController.class, TripDetailsResource.class);
	}

    @Override
    public TripDetailsResource toResource(TripDetailsVO entity) 
    {
    	return TripDetailsResource.builder()
    			.build();
    }

	public TripVO fromResource(TripDetailsResource resource) 
	{
		return TripVO.builder()
			.citizenMobileNumber(resource.getCitizenMobileNumber())
			.isTripClosed(Boolean.FALSE)
			.rcNumber(resource.getRcNumber())
			.sourceLatLang(resource.getSourceLatLang())
			.destiLatLang(resource.getDestiLatLang())
			.shareRoute(resource.isShareRoute())
			.build();
	}

	public TripDetailsResource prepareLatLang(TripDetailsVO event) 
	{
		return TripDetailsResource.builder()
			.sourceLatLang(event.getSourceLatLang())
			.destiLatLang(event.getDestiLatLang())
			.requestTime(event.getRequestTime())
			.tripId(event.getId())
			.citizenMobileNumber(event.getCitizenMobileNumber())
			.isTripExist(event.getIsTripExist())
			.build();
	}
}
