package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadStateSetEvent extends ReadPageEvent<ReadStateSetEvent>
{
	private Long id;
	private String code;
	private String name;
}
