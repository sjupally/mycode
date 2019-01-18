package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadDriverDutyDetailsSetEvent extends ReadPageEvent<ReadDriverDutyDetailsSetEvent>
{
	private Long id;
	private String property;
	private String direction;
	private String dlNumber;
	private String rcNumber;
	private String rfId;
	private String serialNumber;
	private String searchValue;
	private String packetDate;
	private Long districtId;
	private Long cityId;
	private String date;
}

