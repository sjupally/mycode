package com.abhaya.vehicle.tracking.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VehicleLiveLocByDistrictVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Long value;
}
