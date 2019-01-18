package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.DriverDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateDriverDetailsDataEvent 
{
	private DriverDetailsVO driverDetailsVO;
}
