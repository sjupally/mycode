package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.VehicleDetailsQueryController;
import com.abhaya.vehicle.tracking.resource.VehicleDriverDetailsResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

@Component
public class VehicleDriverDetailsResourceAssembler extends ResourceAssemblerSupport<VehicleDriverDetailsVO, VehicleDriverDetailsResource> 
{
    public VehicleDriverDetailsResourceAssembler() 
    {
		super(VehicleDetailsQueryController.class, VehicleDriverDetailsResource.class);
	}

    @Override
    public VehicleDriverDetailsResource toResource(VehicleDriverDetailsVO event) 
    {
    	return VehicleDriverDetailsResource.builder()
				.driverContactNumber(event.getDriverContactNumber())
				.dlExpiryDate(event.getDlExpiryDate() != null ? DateUitls.getStringFromSqlDate(event.getDlExpiryDate(), "dd/MM/yyyy") : null)
				.dlNumber(event.getDlNumber())
				.driverName(event.getDriverName())
				.gender(event.getGender())
				.ownerContactNumber(event.getOwnerContactNumber())
				.ownerName(event.getOwnerName())
				.rcExpiryDate(event.getRcExpiryDate() != null ? DateUitls.getStringFromSqlDate(event.getRcExpiryDate(), "dd/MM/yyyy") : null)
				.rcNumber(event.getRcNumber())
				.registrationDate(event.getRegistrationDate() != null ? DateUitls.getStringFromSqlDate(event.getRegistrationDate(), "dd/MM/yyyy") : null)
				.sourceId(event.getId())
				.vehicleName(event.getVehicleName())
				.rfId(event.getRfId())
				.serialNumber(event.getSerialNumber())
				.packetDate(event.getPacketDate())
				.packetTime(event.getPacketTime())
				.image(event.getImage())
				.build();
    }
}