package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadTravelTrackingSetEvent extends ReadPageEvent<ReadTravelTrackingSetEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String serialNumber;
	private String startDate;
	private String endDate;
	private Long tripId;
	private Long cityId;
	private Long stateId;
	private Long districtId;
	private String cityName;
	private String districtName;
	private String searchValue;
	private String searchDate;
}