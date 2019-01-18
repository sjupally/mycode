package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadCitiesSetEvent extends ReadPageEvent<ReadCitiesSetEvent>
{
	private Long id;
	private String name;
	private String code;
	private Long districtId;
	private String districtName;
	private Long stateId;
	private String stateName;
}
