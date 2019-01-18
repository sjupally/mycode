package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadDriverAuthReportEvent extends ReadPageEvent<ReadDriverAuthReportEvent>
{
	private Long id;
	private Long cityId;
	private Long stateId;
	private Long districtId;
	private String searchDate;
}