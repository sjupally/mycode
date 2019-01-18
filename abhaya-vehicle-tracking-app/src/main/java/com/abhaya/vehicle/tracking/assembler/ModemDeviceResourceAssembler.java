package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.ModemDeviceController;
import com.abhaya.vehicle.tracking.resource.ModemDeviceResource;
import com.abhaya.vehicle.tracking.vos.ModemDetailsVO;

@Component
public class ModemDeviceResourceAssembler extends ResourceAssemblerSupport<ModemDetailsVO, ModemDeviceResource> 
{

	public ModemDeviceResourceAssembler()
	{
		super(ModemDeviceController.class, ModemDeviceResource.class);
	}

	@Override
	public ModemDeviceResource toResource(ModemDetailsVO record) 
	{
		ModemDeviceResource	resource = ModemDeviceResource.builder()
			.modemDeviceId(record.getId())
			.imeiNumber(record.getImeiNumber())
			.imsiNumber(record.getImsiNumber())
			.ipAddress(record.getIpAddress())
			.serialNumber(record.getSerialNumber())
			.signalStrength(record.getSignalStrength())
			.simNumber(record.getSimNumber())
			.version(record.getVersion())
			.createdDate(record.getCreatedDate())
			.mobileNumber(record.getMobileNumber())
			.build();
		return resource;
	}
}
