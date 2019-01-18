package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.CommandExecutionResponseHistoryVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateCommandExecutionResponseHistoryEvent 
{
	private CommandExecutionResponseHistoryVO responseHistoryVO;
}
