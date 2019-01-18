package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.StatusInfoQueryController;
import com.abhaya.vehicle.tracking.resource.StatusInfoResource;
import com.abhaya.vehicle.tracking.vos.VehicleStatusInfoVO;

@Component
public class StatusInfoResourceAssembler extends ResourceAssemblerSupport<VehicleStatusInfoVO, StatusInfoResource> 
{
	public StatusInfoResourceAssembler() 
	{
		super(StatusInfoQueryController.class, StatusInfoResource.class);
	}

	@Override
	public StatusInfoResource toResource(VehicleStatusInfoVO entity) 
	{
		return StatusInfoResource.builder()
				.deviceBatteryStatus(entity.getDeviceBatteryStatus())
				.devicePowerDisconnectStatus(entity.getDevicePowerDisconnectStatus())
				.deviceTamperStatus(entity.getDeviceTamperStatus())
				.dlNumber(entity.getDlNumber())
				.engineStatus(entity.getEngineStatus())
				.ignitionStatus(entity.getIgnitionStatus())
				.iotDeviceDetachedStatus(entity.getIotDeviceDetachedStatus())
				.overSpeed(entity.getOverSpeed())
				.packetDate(entity.getPacketDate())
				.packetTime(entity.getPacketTime())
				.panicButtonForEngineSwithOff(entity.getPanicButtonForEngineSwithOff())
				.panicButtonStatus(entity.getPanicButtonStatus())
				.rcNumber(entity.getRcNumber())
				.rfId(entity.getRfId())
				.serialNumber(entity.getSerialNumber())
				.sleepMode(entity.getSleepMode())
				.statusId(entity.getId())
				.vehicleBatteryStatus(entity.getVehicleBatteryStatus())
				.vehicleIdealStatus(entity.getVehicleIdealStatus())
				.vehicleParkingStatus(entity.getVehicleParkingStatus())
				.vehicleStagnantStatus(entity.getVehicleStagnantStatus())
				.trackId(entity.getTrackId())
				.build();
	}
}
