package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadVehicleStatusInfoReportEvent extends ReadPageEvent<ReadVehicleStatusInfoReportEvent>
{
	private Long id;
	private Long cityId;
	private Long stateId;
	private Long districtId;
	private String date;
}