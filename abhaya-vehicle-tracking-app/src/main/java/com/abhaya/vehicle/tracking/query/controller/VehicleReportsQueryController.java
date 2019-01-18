package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.events.ReadVehicleDetailsSetEvent;
import com.abhaya.vehicle.tracking.services.VehicleDetailsService;
import com.abhaya.vehicle.tracking.utils.VehicleInstSummaryByDistrictVO;
import com.abhaya.vehicle.tracking.vos.VehicleIntsallationSummaryVO;
import com.abhaya.vehicle.tracking.vos.VehicleStoppageSummaryVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class VehicleReportsQueryController 
{
	@Autowired private VehicleDetailsService service;

	@ApiOperation(value = "View list of vehicle Installation Summary", response = VehicleIntsallationSummaryVO.class)
	@RequestMapping(value="getVehicleIntsallationSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleIntsallationSummaryVO getVehicleIntsallationSummary() 
	{
		return service.getVehicleIntsallationSummary();
	}
	
	@ApiOperation(value = "View list of vehicle Stoppage Summary", response = VehicleStoppageSummaryVO.class)
	@RequestMapping(value="getVehicleStoppageSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleStoppageSummaryVO getVehicleStoppageSummary() 
	{
		return service.getVehicleStoppageSummary();
	}

	@ApiOperation(value = "View list of vehicle Installation Summary", response = VehicleIntsallationSummaryVO.class)
	@RequestMapping(value="getVehicleIntSummaryByDistrict", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VehicleInstSummaryByDistrictVO> getVehicleIntSummaryByDistrict(
			@RequestParam(value = "districtId", required = false) Long districtId)
	{
		ReadVehicleDetailsSetEvent request = new ReadVehicleDetailsSetEvent();
		request.setDistrictId(districtId);
		return service.getVehicleIntSummaryByDistrict(request);
	}
}
