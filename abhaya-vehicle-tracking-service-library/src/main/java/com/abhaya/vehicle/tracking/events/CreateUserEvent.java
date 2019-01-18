package com.abhaya.vehicle.tracking.events;

import com.abhaya.vehicle.tracking.vos.UsersDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateUserEvent 
{
	private UsersDetailsVO usersDetailsVO;

}

