package com.abhaya.vehicle.tracking.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.query.controller.VehicleDetailsQueryController;
import com.abhaya.vehicle.tracking.resource.VehicleDetailsResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.utils.DateUtils;
import com.abhaya.vehicle.tracking.vos.VehicleDetailsVO;

@Component
public class VehicleDetailsResourceAssembler extends ResourceAssemblerSupport<VehicleDetailsVO, VehicleDetailsResource> 
{
    public VehicleDetailsResourceAssembler() 
    {
		super(VehicleDetailsQueryController.class, VehicleDetailsResource.class);
	}

    @Override
    public VehicleDetailsResource toResource(VehicleDetailsVO entity) 
    {
    	VehicleDetailsResource resource = createResourceWithId(entity.getId(), entity);
    	resource =  VehicleDetailsResource.builder()
			.vehicleId(entity.getId())
			.ownerContactNumber(entity.getOwnerContactNumber())
			.ownerName(entity.getOwnerName())
			.rcNumber(entity.getRcNumber())
			.registrationDate(DateUitls.getStringFromSqlDate(entity.getRegistrationDate(), "dd/MM/yyyy"))
			.vehicleName(entity.getVehicleName())
			.createdDate(entity.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.cityName(entity.getCityName())
			.serialNumber(entity.getSerialNumber())
			.isOwner(entity.getIsOwner())
			.districtName(entity.getDistrictsName())
			.rcExpiryDate(DateUitls.getStringFromSqlDate(entity.getRcExpiryDate(), "dd/MM/yyyy"))
			.make(entity.getMake())
			.districtId(entity.getDistrictsId())
			.cityId(entity.getCityId())
			.stateId(entity.getStateId())
			.stateName(entity.getStateName())
			.isDeviceMapped(entity.isDeviceMapped())
			.deviceMappedDate(!StringUtils.isEmpty(entity.getDeviceMappedDate()) ? DateUitls.getStringFromTimestamp(entity.getDeviceMappedDate()) : null)
			.build();
    	resource.add(linkTo(methodOn(VehicleDetailsQueryController.class).readDataById(entity.getId())).withSelfRel());
    	return resource;
    }

	public VehicleDetailsVO fromResource(VehicleDetailsResource resource) 
	{
		return VehicleDetailsVO.builder()
				.isOwner(resource.getIsOwner())
				.ownerContactNumber(resource.getOwnerContactNumber())
				.ownerName(resource.getOwnerName())
				.rcNumber(resource.getRcNumber())
				.registrationDate(DateUtils.getSqlDateFromString(resource.getRegistrationDate(), "dd/MM/yyyy"))
				.vehicleName(resource.getVehicleName())
				.make(resource.getMake())
				.cityId(resource.getCityId())
				.districtsId(resource.getDistrictId())
				.rcExpiryDate(DateUtils.getSqlDateFromString(resource.getRcExpiryDate(), "dd/MM/yyyy"))
				.id(resource.getVehicleId())
				.stateId(resource.getStateId())
				.isDeviceMapped(!StringUtils.isEmpty(resource.getIsDeviceMapped())  ? resource.getIsDeviceMapped() : false)
				.deviceMappedDate(!StringUtils.isEmpty(resource.getDeviceMappedDate()) ? DateUtils.getTimeStampDateFromString(resource.getDeviceMappedDate()) : null)
				.serialNumber(resource.getSerialNumber())
				.build();
	}
}