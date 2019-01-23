package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadModemDataSetEvent extends ReadPageEvent<ReadModemDataSetEvent>
{
	private Long id;
	private String serialNumber;
	private String imeiNumber;
	private String searchDate;
	private String sortDirection;
	private String sortColumnName;
	private String searchValue;
	private Long stateId;
	private Long districtId;
	private Long cityId;
}

