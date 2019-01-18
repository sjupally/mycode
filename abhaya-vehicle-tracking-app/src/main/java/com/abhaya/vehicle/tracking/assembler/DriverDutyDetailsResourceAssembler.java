package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.DriverDutyDetailsQueryController;
import com.abhaya.vehicle.tracking.resource.DriverDutyDetailsResource;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

@Component
public class DriverDutyDetailsResourceAssembler extends ResourceAssemblerSupport<VehicleDriverDetailsVO, DriverDutyDetailsResource> 
{
    public DriverDutyDetailsResourceAssembler() 
    {
		super(DriverDutyDetailsQueryController.class, DriverDutyDetailsResource.class);
	}

    @Override
    public DriverDutyDetailsResource toResource(VehicleDriverDetailsVO event) 
    {
    	return DriverDutyDetailsResource.builder()
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
				.build();
    }
}