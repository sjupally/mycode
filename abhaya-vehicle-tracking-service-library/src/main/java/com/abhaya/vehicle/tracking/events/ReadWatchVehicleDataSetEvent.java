package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadWatchVehicleDataSetEvent extends ReadPageEvent<ReadWatchVehicleDataSetEvent>
{
	private Long id;
	private String property;
	private String direction;
	private String rcNumber;
	private String serialNumber;
	private Long stateId;
	private Long cityId;
	private Long districtId;
}
