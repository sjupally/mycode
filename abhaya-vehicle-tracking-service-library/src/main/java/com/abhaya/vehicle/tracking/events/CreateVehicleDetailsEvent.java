package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.VehicleDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateVehicleDetailsEvent 
{
	private VehicleDetailsVO vehicleDetailsVO;
}
