package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.events.ReadPanicSummaryEvent;
import com.abhaya.vehicle.tracking.services.PanicSummaryService;
import com.abhaya.vehicle.tracking.utils.PanicSummaryVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class PanicSummaryQueryController 
{
	@Autowired private PanicSummaryService panicSummaryService;

	@ApiOperation(value = "View list of Panics", response = PanicSummaryVO.class)
	@RequestMapping(value="panicSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PanicSummaryVO> getPanicSummary(
			@RequestParam(value = "cityId" , required = false) Long cityId,
			@RequestParam(value = "stateId" , required = false) Long stateId,
			@RequestParam(value = "districtId" , required = false) Long districtId,
			@RequestParam(value = "eventSource" , required = false) String eventSource,
			@RequestParam(value = "searchDate" , required = false) String searchDate)
	{
		ReadPanicSummaryEvent request = new ReadPanicSummaryEvent();
		request.setEventSource(eventSource);
		request.setCityId(cityId);
		request.setStateId(stateId);
		request.setDistrictId(districtId);
		request.setDate(searchDate);

		List<PanicSummaryVO> list = panicSummaryService.readData(request);
		return list;
	}
}
