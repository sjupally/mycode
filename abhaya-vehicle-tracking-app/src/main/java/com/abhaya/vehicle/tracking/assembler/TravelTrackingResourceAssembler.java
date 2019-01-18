package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.TripTrackingQueryController;
import com.abhaya.vehicle.tracking.resource.TravelTrackingResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.TravelTrackingVO;

@Component
public class TravelTrackingResourceAssembler extends ResourceAssemblerSupport<TravelTrackingVO, TravelTrackingResource> 
{
    public TravelTrackingResourceAssembler() 
    {
		super(TripTrackingQueryController.class, TravelTrackingResource.class);
	}

    @Override
    public TravelTrackingResource toResource(TravelTrackingVO entity) 
    {
    	return TravelTrackingResource.builder().
				packetDate(entity.getPacketDate()).
				packetTime(entity.getPacketTime()).
				createdDate(entity.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null).
				serialNumber(entity.getSerialNumber()).
				latitude(entity.getLatitude()).
				langitude(entity.getLangitude()).
				time(entity.getTime()).
				hdop(entity.getHdop()).
				altitude(entity.getAltitude()).
				fix(entity.getFix()).
				cog(entity.getCog()).
				spkm(entity.getSpkm()).
				spkn(entity.getSpkn()).
				date(entity.getDate()).
				nsat(entity.getNsat()).
				imeiNumber(entity.getImeiNumber()).
				sourceId(entity.getId()).
				location(entity.getLocation())
			   .build();
    }
	public TravelTrackingVO fromResource(TravelTrackingResource resource) 
	{
		return TravelTrackingVO.builder()
			.build();
	}
}
