package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadDeviceCommunicationEvent extends ReadPageEvent<ReadDeviceCommunicationEvent> 
{
    private String status;
    private Long stateId;
    private Long districtId;
    private Long cityId;
    private String searchValue;
    private String searchDate;
    private String movement;
	private String batteryStatus;
	private String ignitionStatus;
	private String engineStatus;
	private String tamperStatus;
	private String panicButtonStatus;
	private Boolean isDistrictWise;
}