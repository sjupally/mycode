package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadRouteDeviationSetEvent extends ReadPageEvent<ReadRouteDeviationSetEvent>
{
	private Long id;
	private String property;
	private String direction;
	private String dlNumber;
	private String rcNumber;
	private String citizenMobileNumber;
	private String serialNumber;
	private String rfId;
	private String searchValue;
	private String searchDate;
	private Long tripId;
}
