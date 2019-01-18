package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadDistrictsSetEvent extends ReadPageEvent<ReadDistrictsSetEvent>
{
	private Long id;
	private String name;
	private String description;
	private Long stateId;
	private String stateName;
}
