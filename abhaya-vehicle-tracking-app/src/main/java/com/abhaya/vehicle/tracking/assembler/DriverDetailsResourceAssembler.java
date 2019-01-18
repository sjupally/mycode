package com.abhaya.vehicle.tracking.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.DriverDetailsQueryController;
import com.abhaya.vehicle.tracking.resource.DriverDetailsResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.DriverDetailsVO;

@Component
public class DriverDetailsResourceAssembler extends ResourceAssemblerSupport<DriverDetailsVO, DriverDetailsResource> 
{
	public DriverDetailsResourceAssembler() 
	{
		super(DriverDetailsQueryController.class, DriverDetailsResource.class);
	}

	@Override
	public DriverDetailsResource toResource(DriverDetailsVO entity) 
	{
		return DriverDetailsResource.builder()
			.driverId(entity.getId())
			.contactNumber(entity.getContactNumber())
			.createdDate(DateUitls.getStringFromTimestamp(entity.getCreatedDate()))
			.dlExpiryDate(DateUitls.getStringFromSqlDate(entity.getDlExpiryDate(), "dd/MM/yyyy"))
			.dlNumber(entity.getDlNumber())
			.driverName(entity.getDriverName())
			.gender(entity.getGender())
			.cityName(entity.getCityName())
			.districtName(entity.getDistrictsName())
			.districtId(entity.getDistrictsId())
			.cityId(entity.getCityId())
			.stateId(entity.getStateId())
			.stateName(entity.getStateName())
			.rfId(entity.getRfId())
			.image(entity.getImage())
			.build();
	}

	public DriverDetailsVO fromResource(DriverDetailsResource resource) throws IOException 
	{
		return DriverDetailsVO.builder()
			.contactNumber(resource.getContactNumber())
			.dlExpiryDate(DateUitls.getSqlDateFromString(resource.getDlExpiryDate()))
			.dlNumber(resource.getDlNumber())
			.driverName(resource.getDriverName())
			.gender(resource.getGender())
			.id(resource.getDriverId())
			.rfId(resource.getRfId())
			.districtsId(resource.getDistrictId())
			.cityId(resource.getCityId())
			.stateId(resource.getStateId())
			.image(resource.getFile() != null ? resource.getFile().getBytes() : null)
			.build();
	}
}