package com.abhaya.vehicle.tracking.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.DistressDetailsQueryController;
import com.abhaya.vehicle.tracking.resource.DistressDetailsResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.DistressDetailsVO;

@Component
public class DistressDetailsResourceAssembler extends ResourceAssemblerSupport<DistressDetailsVO, DistressDetailsResource> 
{
	public DistressDetailsResourceAssembler() 
	{
		super(DistressDetailsQueryController.class, DistressDetailsResource.class);
	}

	@Override
	public DistressDetailsResource toResource(DistressDetailsVO record) 
	{
		DistressDetailsResource resource = createResourceWithId(record.getId(), record);
		resource =  DistressDetailsResource.builder()
				.distressId(record.getId())
				.citizenMobileNumber(record.getCitizenMobileNumber())
				.createdDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
				.distressLocation(record.getDistressLocation())
				.dlNumber(record.getDlNumber())
				.driverMobileNumber(record.getDriverMobileNumber())
				.driverName(record.getDriverName())
				.packetDate(record.getPacketDate())
				.packetTime(record.getPacketTime())
				.rcNumber(record.getRcNumber())
				.rfId(record.getRfId())
				.serialNumber(record.getSerialNumber())
				.tripRequestId(record.getTripRequestId())
				.tripRequestTime(record.getTripRequestTime() != null ? DateUitls.getStringFromTimestamp(record.getTripRequestTime()) : null)
				.isClosed(record.isClosed())
				.tripId(record.getTripId())
				.eventType(record.getEventType())
			.build();
		resource.add(linkTo(methodOn(DistressDetailsQueryController.class).readDataById(record.getId())).withSelfRel());
		return resource;
	}
}