package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadPanicSummaryEvent extends ReadPageEvent<ReadPanicSummaryEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String eventSource;
	private Boolean isClosed;	
	private Long count;
	private Long cityId;
	private Long districtId;
	private Long stateId;
	private String date;
	private String searchValue;
}