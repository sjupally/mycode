package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.RawPacketDataQueryController;
import com.abhaya.vehicle.tracking.resource.RawPacketDataResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.RawDataPacketVO;

@Component
public class RawPacketDataResourceAssembler extends ResourceAssemblerSupport<RawDataPacketVO, RawPacketDataResource> 
{

	public RawPacketDataResourceAssembler()
	{
		super(RawPacketDataQueryController.class, RawPacketDataResource.class);
	}

	@Override
	public RawPacketDataResource toResource(RawDataPacketVO record) 
	{
		return RawPacketDataResource.builder()
			.imeiNumber(record.getImeiNumber())
			.serialNumber(record.getSerialNumber())
			.createdDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
			.packetDate(record.getPacketDate())
			.packetTime(record.getPacketTime())
			.rawData(record.getRawData())
			.rawDataId(record.getId())
			.build();
	}
}