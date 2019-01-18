package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.WatchVehicleQueryController;
import com.abhaya.vehicle.tracking.resource.WatchVehicleResource;
import com.abhaya.vehicle.tracking.vos.WatchVehicleVO;

@Component
public class WatchVehicleResourceAssembler extends ResourceAssemblerSupport<WatchVehicleVO, WatchVehicleResource> 
{
    public WatchVehicleResourceAssembler() 
    {
		super(WatchVehicleQueryController.class, WatchVehicleResource.class);
	}

    @Override
    public WatchVehicleResource toResource(WatchVehicleVO record) 
    {
    	WatchVehicleResource resource = createResourceWithId(record.getId(), record);
    	 resource = WatchVehicleResource.builder()
			.watchId(record.getId())
			.createdDate(record.getCreatedDate())
			.vehicleId(record.getVehicleId())
            .serialNumber(record.getSerialNumber())
            .rcNumber(record.getRcNumber())
            .packetDate(record.getPacketDate())
            .packetTime(record.getPacketTime())
            .langitude(record.getLangitude())
            .latitude(record.getLatitude())
            .location(record.getLocation())
            .imeiNumber(record.getImeiNumber())
            .status(record.getStatus())
            .stateId(record.getStateId())
            .districtId(record.getDistrictId())
            .cityId(record.getCityId())
            .stateName(record.getStateName())
            .districtName(record.getDistrictName())
            .cityName(record.getCityName())
            .movement(record.getMovement())
            .batteryStatus(record.getBatteryStatus())
            .ignitionStatus(record.getIgnitionStatus())
            .engineStatus(record.getEngineStatus())
            .tamperStatus(record.getTamperStatus())
            .panicButtonStatus(record.getPanicButtonStatus())
			.build();
    	 return resource;
    }

	public WatchVehicleVO fromResource(WatchVehicleResource resource) 
	{
		return WatchVehicleVO.builder()
				.rcNumber(resource.getRcNumber())
				.location(resource.getLocation())
				.build();
	}
}