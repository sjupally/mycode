package com.abhaya.vehicle.tracking.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties (ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@ToString
public class VehicleDashboardVO 
{
	private Long id;
	private String serialNumber;
	private String latitude;
	private String langitude;
	private String rcNumber;
	
	private Long cityId;
	private Long stateId;
	private Long districtId;
	private String cityName;
	private String districtName;
}
