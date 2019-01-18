package com.abhaya.vehicle.tracking.query.controller;

import com.abhaya.vehicle.tracking.assembler.TravelTrackingResourceAssembler;
import com.abhaya.vehicle.tracking.assembler.VehicleLiveStatusResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadTravelTrackingSetEvent;
import com.abhaya.vehicle.tracking.events.ReadTripDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.TravelTrackingResource;
import com.abhaya.vehicle.tracking.resource.VehicleLiveStatusResource;
import com.abhaya.vehicle.tracking.services.TripTrackingDetailsService;
import com.abhaya.vehicle.tracking.utils.TripSummaryVO;
import com.abhaya.vehicle.tracking.utils.VehicleDashboardVO;
import com.abhaya.vehicle.tracking.utils.VehicleLiveLocByDistrictVO;
import com.abhaya.vehicle.tracking.vos.TravelTrackingVO;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("v1")
public class TripTrackingQueryController 
{
	@Autowired private TripTrackingDetailsService service;
	@Autowired private TravelTrackingResourceAssembler assembler;
	@Autowired private VehicleLiveStatusResourceAssembler assembler2;

	@ApiOperation(value = "View list of Trip Tracking Details based on Device ID", response = ResponseEntity.class)
	@RequestMapping(value="tripTrackingDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TravelTrackingResource>> travelTrackingDetails(@RequestParam(value = "serialNumber" , required = false) String serialNumber,
			@RequestParam(value = "startDate" , required = false) String startDate,@RequestParam(value = "tripId" , required = false) Long tripId,@RequestParam(value = "traceId" , required = false) Long traceId,
			@RequestParam(value = "searchValue", required = false) String searchValue,@RequestParam(value = "searchDate", required = false) String searchDate,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<TravelTrackingVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadTravelTrackingSetEvent request = new ReadTravelTrackingSetEvent();
		request.setSerialNumber(serialNumber);
		request.setStartDate(startDate);
		request.setEndDate(startDate);
		request.setTripId(tripId);
		request.setId(traceId);
		request.setSearchValue(searchValue);
		request.setSearchDate(searchDate);
		request.setPageable(pageable);

		PageReadEvent<TravelTrackingVO> event = service.readTripTrackingData(request);
		Page<TravelTrackingVO> page = event.getPage();
		PagedResources<TravelTrackingResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@ApiOperation(value = "View list of Live Vehicles", response = VehicleDashboardVO.class)
	@RequestMapping(value="getLiveVehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<VehicleLiveStatusResource>> getLiveVehicles(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			@RequestParam(value = "cityId" , required = false) Long cityId,@RequestParam(value = "stateId" , required = false) Long stateId,
			@RequestParam(value = "districtId" , required = false) Long districtId,@RequestParam(value = "districtName" , required = false) String districtName,
			@RequestParam(value = "cityName" , required = false) String cityName,
			PagedResourcesAssembler<VehicleDashboardVO> pagedAssembler)
	{
		ReadTravelTrackingSetEvent request = new ReadTravelTrackingSetEvent();
		request.setPageable(pageable);
		request.setCityId(cityId);
		request.setStateId(stateId);
		request.setDistrictId(districtId);
		request.setDistrictName(districtName);
		request.setCityName(cityName);

		PageReadEvent<VehicleDashboardVO> event = service.getLiveVehicles(request);
		Page<VehicleDashboardVO> page = event.getPage();
		PagedResources<VehicleLiveStatusResource> pagedResources = pagedAssembler.toResource(page, assembler2);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@ApiOperation(value = "View Latest live vehicle location", response = ResponseEntity.class)
	@RequestMapping(value="getLatestLiveLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TravelTrackingResource> getLatestLiveLocation(@RequestParam(value = "serialNumber" , required = true) String serialNumber)
	{
		ReadTravelTrackingSetEvent request = new ReadTravelTrackingSetEvent();
		request.setSerialNumber(serialNumber);

		TravelTrackingVO travelTrackingVO = service.readLiveVehicleLocation(request);
		TravelTrackingResource travelTrackingResource = assembler.toResource(travelTrackingVO);
		return new ResponseEntity<>(travelTrackingResource, HttpStatus.OK);
	}

	@ApiOperation(value = "View Latest live vehicle location", response = ResponseEntity.class)
	@RequestMapping(value="getLatestLiveLocCountByDist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VehicleLiveLocByDistrictVO> getLatestLiveLocCountByDist(@RequestParam(value = "districtId" , required = false) Long districtId)
	{
		ReadTravelTrackingSetEvent request = new ReadTravelTrackingSetEvent();
		request.setDistrictId(districtId);

		return service.getLatestLiveLocCountByDist(request);
	}

	@ApiOperation(value = "Get Total Number of Trips Count", response = TripSummaryVO.class)
	@RequestMapping(value="getTripsSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TripSummaryVO> getTripsSummary(@RequestParam(value = "searchDate" , required = false) String searchDate,
											   @RequestParam(value = "isDistrictWise", required = false, defaultValue = "false") Boolean isDistrictWise)
	{
		ReadTripDetailsSetEvent request = new ReadTripDetailsSetEvent();
		request.setSearchValue(searchDate);
		request.setIsDistrictWise(isDistrictWise);

		return service.getTripsSummary(request);
	}

	@ApiOperation(value = "Get Total Number of Trips Count", response = TripSummaryVO.class)
	@RequestMapping(value="getRouteDeviatedAndPanicSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TripSummaryVO> getRouteDeviatedAndPanicSummary(@RequestParam(value = "searchDate" , required = false) String searchDate,
															   @RequestParam(value = "isDistrictWise", required = false, defaultValue = "false") Boolean isDistrictWise)
	{
		ReadTripDetailsSetEvent request = new ReadTripDetailsSetEvent();
		request.setSearchValue(searchDate);
		request.setIsDistrictWise(isDistrictWise);
		return service.getRouteDeviatedAndPanicSummary(request);
	}

}
