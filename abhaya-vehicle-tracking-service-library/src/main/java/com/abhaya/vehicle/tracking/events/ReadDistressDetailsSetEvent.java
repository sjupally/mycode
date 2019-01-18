package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadDistressDetailsSetEvent extends ReadPageEvent<ReadDistressDetailsSetEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String citizenMobileNumber;
	private String dlNumber;
	private String serialNumber;
	private String rcNumber;
	private Boolean isClosed;
	private String eventType;
	private Long stateId;
	private Long cityId;
	private Long districtId;
	private String searchValue;
	private String searchDate;
	private Long tripId;
}