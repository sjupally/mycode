package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadPoliceStationDetailsSetEvent extends ReadPageEvent<ReadPoliceStationDetailsSetEvent>
{
	private Long id;
	private String sortDirection;
	private String sortColumnName;
	private Long mobileNumber;
	private String stationName;
}
