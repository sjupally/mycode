package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.WatchVehicleVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateWatchVehicleEvent 
{
	private WatchVehicleVO watchVehicleVO;

}