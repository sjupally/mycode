package com.abhaya.vehicle.tracking.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.RouteDeviationResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadRouteDeviationSetEvent;
import com.abhaya.vehicle.tracking.resource.RouteDeviationResource;
import com.abhaya.vehicle.tracking.services.RouteDeviationService;
import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/routeDeviation")
public class RouteDeviationQueryController 
{
	@Autowired private RouteDeviationService service;
	@Autowired private RouteDeviationResourceAssembler assembler;

	@ApiOperation(value = "View list of Route Deviations", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<RouteDeviationResource>> readVehicleData(
			@RequestParam(value = "rcNumber", required = false) String rcNumber,
			@RequestParam(value = "rfId", required = false) String rfId,
			@RequestParam(value = "property", required = false) String property,
			@RequestParam(value = "serialNumber", required = false) String serialNumber,
			@RequestParam(value = "direction", required = false) String direction,
			@RequestParam(value = "dlNumber", required = false) String dlNumber,
			@RequestParam(value = "citizenMobileNumber", required = false) String citizenMobileNumber,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@RequestParam(value = "tripId", required = false) Long tripId,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<RouteDeviationVO> pagedAssembler) 
	{
		ReadRouteDeviationSetEvent request = new ReadRouteDeviationSetEvent();
		request.setPageable(pageable);
		request.setCitizenMobileNumber(citizenMobileNumber);
		request.setRcNumber(rcNumber);
		request.setSerialNumber(serialNumber);
		request.setRfId(rfId);
		request.setDlNumber(request.getDlNumber());
		request.setSearchValue(searchValue);
		request.setSearchDate(searchDate);
		request.setTripId(tripId);

		PageReadEvent<RouteDeviationVO> event = service.readData(request);
		Page<RouteDeviationVO> page = event.getPage();
		PagedResources<RouteDeviationResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
