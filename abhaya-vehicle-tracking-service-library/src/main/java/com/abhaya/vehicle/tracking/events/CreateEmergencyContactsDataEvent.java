package com.abhaya.vehicle.tracking.events;

import java.util.List;

import com.abhaya.vehicle.tracking.vos.EmergencyContactNumbersVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateEmergencyContactsDataEvent 
{
	
	private List<EmergencyContactNumbersVO> emergencyContactNumbersVOs;

}
