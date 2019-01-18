package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.mobile.command.controller.RouteDeviationCommandController;
import com.abhaya.vehicle.tracking.resource.RouteDeviationResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;

@Component
public class RouteDeviationResourceAssembler extends ResourceAssemblerSupport<RouteDeviationVO, RouteDeviationResource> 
{
	public RouteDeviationResourceAssembler() 
	{
		super(RouteDeviationCommandController.class, RouteDeviationResource.class);
	}

	@Override
	public RouteDeviationResource toResource(RouteDeviationVO entity) 
	{
		return RouteDeviationResource.builder()
				.citizenMobileNumber(entity.getCitizenMobileNumber())
				.location(entity.getLocation())
				.rcNumber(entity.getRcNumber())
				.deviationId(entity.getId())
				.deviationTime(!StringUtils.isEmpty(entity.getDeviationTime()) ? DateUitls.getStringFromTimestamp(entity.getDeviationTime()) : null)
				.dlNumber(entity.getDlNumber())
				.driverName(entity.getDriverName())
				.rfId(entity.getRfId())
				.serialNumber(entity.getSerialNumber())
				.tripRequestId(entity.getRequestId())
				.geoLocation(entity.getGeoLocation())
				.requestId(entity.getRequestId())
				.rfId(entity.getRfId())
				.serialNumber(entity.getSerialNumber())
				.stateId(entity.getStateId())
				.districtId(entity.getDistrictId())
				.cityId(entity.getCityId())
				.sLatLng(entity.getSLatLng())
				.sLocation(entity.getSLocation())
				.dLatLng(entity.getDLatLng())
				.dLocation(entity.getDLocation())
				.driverContactNumber(entity.getDriverContactNumber())
				.ownerContactNumber(entity.getOwnerContactNumber())
				.ownerName(entity.getOwnerName())
				.tripId(entity.getTripId())
			.build();
	}

	public RouteDeviationVO fromResource(RouteDeviationResource resource) 
	{
		return RouteDeviationVO.builder()
			.citizenMobileNumber(resource.getCitizenMobileNumber())
			.location(resource.getLocation())
			.rcNumber(resource.getRcNumber())
			.build();
	}
}