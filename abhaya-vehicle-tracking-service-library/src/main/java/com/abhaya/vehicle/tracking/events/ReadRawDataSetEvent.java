package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadRawDataSetEvent extends ReadPageEvent<ReadRawDataSetEvent>
{
	private Long id;
	private String serialNumber;
	private String imeiNumber;
	private String fromDate;
	private String toDate;
	private String sortDirection;
	private String sortColumnName;
	private String packetDate;
	private String searchValue;
}