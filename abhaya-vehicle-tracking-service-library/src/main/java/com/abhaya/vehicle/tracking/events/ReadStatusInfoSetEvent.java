package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadStatusInfoSetEvent extends ReadPageEvent<ReadStatusInfoSetEvent>
{
	private Long id;
	private String property;
	private String direction;
	private String dlNumber;
	private String rcNumber;
	private String serialNumber;
	private String rfId;
	private Long vehicleId;
	private Long trackId;
}

