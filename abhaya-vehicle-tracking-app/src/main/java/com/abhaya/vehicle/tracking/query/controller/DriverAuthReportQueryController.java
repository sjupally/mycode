package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.events.ReadDriverAuthReportEvent;
import com.abhaya.vehicle.tracking.services.DriverDutyDetailsService;
import com.abhaya.vehicle.tracking.utils.DriverAuthVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class DriverAuthReportQueryController 
{
	
	@Autowired private DriverDutyDetailsService driverDutyDetailsService;

	

	@ApiOperation(value = "View list of Authenticated Vehicles", response = DriverAuthVO.class)
	@RequestMapping(value="getAuthVehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DriverAuthVO> getAuthVehicles(
			@RequestParam(value = "cityId" , required = false) Long cityId,
			@RequestParam(value = "stateId" , required = true) Long stateId,
			@RequestParam(value = "districtId" , required = false) Long districtId,
			@RequestParam(value = "searchDate", required = false) String searchDate)
	{
		ReadDriverAuthReportEvent request = new ReadDriverAuthReportEvent();
		request.setCityId(cityId);
		request.setStateId(stateId);
		request.setDistrictId(districtId);
		request.setSearchDate(searchDate);

		List<DriverAuthVO> list = driverDutyDetailsService.readDriverAuthData(request);
		return list;
	}
}
