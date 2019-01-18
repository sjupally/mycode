package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ReadDriverDetailsSetEvent extends ReadPageEvent<ReadDriverDetailsSetEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String driverName;
	private String dlNumber;
	private String searchValue;
	private String rfId;
	private Long districtId;
	private Long cityId;
	private String date;
	private String driverContactNumber;
}