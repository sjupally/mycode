package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ToString
public class ReadEmergencyContactsSetEvent extends ReadPageEvent<ReadEmergencyContactsSetEvent>
{
	private Long id;
	private String direction;
	private String property;
	private String citizenMobileNumber;
	private String emergencyContactNumber;
}