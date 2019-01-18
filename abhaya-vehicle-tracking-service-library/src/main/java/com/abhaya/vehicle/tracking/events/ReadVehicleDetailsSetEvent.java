package com.abhaya.vehicle.tracking.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadVehicleDetailsSetEvent extends ReadPageEvent<ReadVehicleDetailsSetEvent>
{
	private Long id;
	private String property;
	private String direction;
	private Long mobileNumber;
	private String rcNumber;
	private String serialNumber;
	private String searchValue;
	private Boolean isDeviceMapped;
	private Long districtId;
	private Long cityId;
	private String date;
}
