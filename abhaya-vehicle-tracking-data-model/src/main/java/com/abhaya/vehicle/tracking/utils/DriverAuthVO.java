package com.abhaya.vehicle.tracking.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@ToString
public class DriverAuthVO 
{
	private String authType;
	private Long count;
	private Long cityId;
	private Long districtId;
	private Long stateId;
	private String searchDate;
	
}
