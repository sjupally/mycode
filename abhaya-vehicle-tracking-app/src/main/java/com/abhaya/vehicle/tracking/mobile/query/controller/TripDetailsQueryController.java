package com.abhaya.vehicle.tracking.mobile.query.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

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

import com.abhaya.vehicle.tracking.assembler.TravelTrackingResourceAssembler;
import com.abhaya.vehicle.tracking.assembler.TripDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.assembler.TripDetailsViewResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadTravelTrackingSetEvent;
import com.abhaya.vehicle.tracking.events.ReadTripDetailsSetEvent;
import com.abhaya.vehicle.tracking.model.ESTripTrackingDetails;
import com.abhaya.vehicle.tracking.model.EsTripDetails;
import com.abhaya.vehicle.tracking.resource.TravelTrackingResource;
import com.abhaya.vehicle.tracking.resource.TripDetailsResource;
import com.abhaya.vehicle.tracking.resource.TripDetailsViewResource;
import com.abhaya.vehicle.tracking.services.ESTripDetailsService;
import com.abhaya.vehicle.tracking.services.TripDetailsService;
import com.abhaya.vehicle.tracking.services.TripTrackingDetailsService;
import com.abhaya.vehicle.tracking.vos.TravelTrackingVO;
import com.abhaya.vehicle.tracking.vos.TripDetailsVO;
import com.abhaya.vehicle.tracking.vos.TripDetailsViewVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mobile")
public class TripDetailsQueryController 
{
	@Autowired private TripDetailsService service;
	@Autowired private ESTripDetailsService esTripDetailsService;
	@Autowired private TripTrackingDetailsService tripTrackingDetailsService;
	@Autowired private TripDetailsResourceAssembler assembler;
	@Autowired private TripDetailsViewResourceAssembler tripDetailsViewResourceAssembler;
	@Autowired private TravelTrackingResourceAssembler trackingResourceAssembler;

	@ApiOperation(value = "View list of Vehicles", response = ResponseEntity.class)
	@RequestMapping(value="getLatLng", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TripDetailsResource> getTripLatLang(String vehicleNumber) 
	{
		TripDetailsVO event = service.getTripLatLang(vehicleNumber);
		if (event != null)
		{
			TripDetailsResource resource = assembler.prepareLatLang(event);
			return new ResponseEntity<>(resource, HttpStatus.OK);
		}
		return null;
	}
	@ApiOperation(value = "Check Trip extist or not with given Vehicle Number and  Citizen number", response = ResponseEntity.class)
	@RequestMapping(value="isTripExist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TripDetailsResource> isTripExist(String vehicleNumber,String citizenMobileNumber) 
	{
		TripDetailsVO event = service.isTripExist(vehicleNumber,citizenMobileNumber);
		if (event != null)
		{
			TripDetailsResource resource = assembler.prepareLatLang(event);
			return new ResponseEntity<>(resource, HttpStatus.OK);
		}
		return null;
	}
	
	@ApiOperation(value = "Get Current location", response = ResponseEntity.class)
	@RequestMapping(value="currentLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TripDetailsResource> getCurrentLocation(String vehicleNumber) 
	{
		TripDetailsVO event = service.getCurrentLocation(vehicleNumber);
		if (event != null)
		{
			TripDetailsResource resource = assembler.prepareLatLang(event);
			return new ResponseEntity<>(resource, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "View list of Trip Details for Google Map", response = ResponseEntity.class)
	@RequestMapping(value="tripTrackingDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripDetailsViewResource>> travelTrackingDetails(ReadTripDetailsSetEvent request,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<TripDetailsViewVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		request.setPageable(pageable);
		PageReadEvent<TripDetailsViewVO> event = service.readTripDetails(request);
		Page<TripDetailsViewVO> page = event.getPage();
		PagedResources<TripDetailsViewResource> pagedResources = pagedAssembler.toResource(page, tripDetailsViewResourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get Trip Tracking Based on TripId and trackId", response = EsTripDetails.class)
	@RequestMapping(value="es/tripTrackingDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ESTripTrackingDetails> findByTripIdAndTrackId(@QueryParam("tripId") Long tripId,@QueryParam("trackId") Long trackId) 
    {
    	return esTripDetailsService.findByTripIdAndTrackId(tripId, trackId);
    }

	@ApiOperation(value = "View list of Trip Tracking Details based on Device ID", response = ResponseEntity.class)
	@RequestMapping(value="tripTraceDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TravelTrackingResource>> travelTrackingDetails(@RequestParam(value = "serialNumber" , required = false) String serialNumber,
			@RequestParam(value = "startDate" , required = false) String startDate,@RequestParam(value = "tripId" , required = false) Long tripId,@RequestParam(value = "traceId" , required = false) Long traceId,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
			PagedResourcesAssembler<TravelTrackingVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadTravelTrackingSetEvent request = new ReadTravelTrackingSetEvent();
		request.setPageable(pageable);
		request.setSerialNumber(serialNumber);
		request.setStartDate(startDate);
		request.setEndDate(startDate);
		request.setTripId(tripId);
		request.setId(traceId);

		PageReadEvent<TravelTrackingVO> event = tripTrackingDetailsService.readLiveTrackingData(request);
		Page<TravelTrackingVO> page = event.getPage();
		PagedResources<TravelTrackingResource> pagedResources = pagedAssembler.toResource(page, trackingResourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@ApiOperation(value = "View list of Trip Details", response = ResponseEntity.class)
	@RequestMapping(value="tripDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripDetailsViewResource>> getTripDetails(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,
																				  PagedResourcesAssembler<TripDetailsViewVO> pagedAssembler,
																				  @RequestParam(value = "isTripClosed" , required = false) Boolean isTripClosed,
																				  @RequestParam(value = "driverContactNumber" , required = false) String driverContactNumber)
	{
		ReadTripDetailsSetEvent request = new ReadTripDetailsSetEvent();
		request.setIsTripClosed(isTripClosed);
		request.setDriverContactNumber(driverContactNumber);
		request.setPageable(pageable);

		PageReadEvent<TripDetailsViewVO> event = service.readTripDetails(request);
		Page<TripDetailsViewVO> page = event.getPage();
		PagedResources<TripDetailsViewResource> pagedResources = pagedAssembler.toResource(page, tripDetailsViewResourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
