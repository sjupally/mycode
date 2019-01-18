package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadCommandSettingsSetEvent extends ReadPageEvent<ReadCommandSettingsSetEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String commandName;
}