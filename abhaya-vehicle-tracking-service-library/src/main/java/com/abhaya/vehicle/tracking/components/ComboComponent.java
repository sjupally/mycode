package com.abhaya.vehicle.tracking.components;

import java.util.List;

import com.abhaya.vehicle.tracking.vos.ComboDataVO;

public interface ComboComponent 
{
	public List<ComboDataVO> getData4Combo(String... extraParams);
}
