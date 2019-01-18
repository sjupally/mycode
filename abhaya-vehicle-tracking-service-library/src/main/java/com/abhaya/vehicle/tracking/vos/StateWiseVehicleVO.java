package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.util.List;

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
public class StateWiseVehicleVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int totalVehicles;
	private int liveTracking ;
	private int routeDeviation ;
	private int ignitionOff ;
	private String stateName;
	private List<DistrictwiseVehiclesVO> districts;
	
}