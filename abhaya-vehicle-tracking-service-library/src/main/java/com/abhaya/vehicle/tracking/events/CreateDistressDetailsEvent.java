package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.DistressDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
@Accessors(chain = true)
public class CreateDistressDetailsEvent 
{
	private DistressDetailsVO distressDetailsVO;
}
