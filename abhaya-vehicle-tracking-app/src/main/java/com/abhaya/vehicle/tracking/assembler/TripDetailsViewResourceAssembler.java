package com.abhaya.vehicle.tracking.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.TripDetailsViewQueryController;
import com.abhaya.vehicle.tracking.resource.TripDetailsViewResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.TripDetailsViewVO;

@Component
public class TripDetailsViewResourceAssembler extends ResourceAssemblerSupport<TripDetailsViewVO, TripDetailsViewResource> 
{
    public TripDetailsViewResourceAssembler() 
    {
		super(TripDetailsViewQueryController.class, TripDetailsViewResource.class);
	}

    @Override
    public TripDetailsViewResource toResource(TripDetailsViewVO record) 
    {
    	TripDetailsViewResource resource = createResourceWithId(record.getId(), record);
    	resource =  TripDetailsViewResource.builder()
    			.citizenMobileNumber(record.getCitizenMobileNumber())
				.closeTime(record.getCloseTime() != null ? DateUitls.getStringFromTimestamp(record.getCloseTime()) : null)
				.destiLatLang(record.getDestiLatLang())
				.dlNumber(record.getDlNumber())
				.driverContactNumber(record.getDriverContactNumber())
				.driverName(record.getDriverName())
				.tripId(record.getId())
				.isTripClosed(record.isTripClosed())
				.rcNumber(record.getRcNumber())
				.remarks(record.getRemarks())
				.requestId(record.getRequestId())
				.requestTime(record.getRequestTime() != null ? DateUitls.getStringFromTimestamp(record.getRequestTime()) : null)
				.rfId(record.getRfId())
				.sourceLatLang(record.getSourceLatLang())
				.travelMode(record.getTravelMode())
				.serialNumber(record.getSerialNumber())
				.sourceLocation(record.getSourceLocation())
				.destiLocation(record.getDestiLocation())
				.driverAddress(record.getDriverAddress())
				.ownerContactNumber(record.getOwnerContactNumber())
				.ownerName(record.getOwnerName())
				.vehicleAddress(record.getVehicleAddress())
    			.build();
    	resource.add(linkTo(methodOn(TripDetailsViewQueryController.class).readDataById(record.getId())).withSelfRel());
    	return resource;
    }
}
