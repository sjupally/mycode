package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateRouteDeviationEvent 
{
	private RouteDeviationVO routeDeviationVO;
}