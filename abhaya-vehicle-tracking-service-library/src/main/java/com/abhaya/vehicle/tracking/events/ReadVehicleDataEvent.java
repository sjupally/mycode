package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadVehicleDataEvent extends ReadEntityEvent<ReadVehicleDataEvent>{
	
	private Long id;

}