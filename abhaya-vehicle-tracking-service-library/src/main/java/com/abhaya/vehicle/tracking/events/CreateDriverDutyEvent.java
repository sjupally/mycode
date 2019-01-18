package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.DriverRFIDVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateDriverDutyEvent 
{
	private DriverRFIDVO driverRFIDVO;
}