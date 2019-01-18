package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.DeviceCommunicationQueryController;
import com.abhaya.vehicle.tracking.resource.DeviceCommunicationResource;
import com.abhaya.vehicle.tracking.vos.DeviceCommunicationVO;

@Component
public class DeviceCommunicationResourceAssembler extends ResourceAssemblerSupport<DeviceCommunicationVO, DeviceCommunicationResource> {
    public DeviceCommunicationResourceAssembler() {
        super(DeviceCommunicationQueryController.class, DeviceCommunicationResource.class);
    }

    @Override
    public DeviceCommunicationResource toResource(DeviceCommunicationVO record) {
        DeviceCommunicationResource resource = createResourceWithId(record.getId(), record);
        resource = DeviceCommunicationResource.builder()
                .serialNumber(record.getSerialNumber())
                .rcNumber(record.getRcNumber())
                .createdDate(record.getCreatedDate())
                .packetDate(record.getPacketDate())
                .packetTime(record.getPacketTime())
                .langitude(record.getLangitude())
                .latitude(record.getLatitude())
                .location(record.getLocation())
                .prevPacketDate(record.getPrevPacketDate())
                .prevPacketTime(record.getPrevPacketTime())
                .prevLangitude(record.getPrevLangitude())
                .prevLatitude(record.getPrevLatitude())
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
}