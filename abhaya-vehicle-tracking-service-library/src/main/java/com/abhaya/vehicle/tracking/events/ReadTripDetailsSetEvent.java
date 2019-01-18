package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadTripDetailsSetEvent extends ReadPageEvent<ReadTripDetailsSetEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String citizenMobileNumber;
	private String driverContactNumber;
	private String dlNumber;
	private String rcNumber;
	private String requestId;
	private Boolean isTripClosed;
	private String startDate;
	private String serialNumber;
	private String searchValue;
	private Long stateId;
	private Long cityId;
	private Long districtId;
	private Boolean isDistrictWise;
}

