package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.model.ESDistressDetails;
import com.abhaya.vehicle.tracking.model.ESDriverDutyDetails;
import com.abhaya.vehicle.tracking.model.ESRouteDeviation;
import com.abhaya.vehicle.tracking.model.ESTripTrackingDetails;
import com.abhaya.vehicle.tracking.model.EsTripDetails;
import com.abhaya.vehicle.tracking.services.ESDistressDetailsService;
import com.abhaya.vehicle.tracking.services.ESDriverDutyDetailsService;
import com.abhaya.vehicle.tracking.services.ESRouteDeviationService;
import com.abhaya.vehicle.tracking.services.ESTripDetailsService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("v1/es")
public class ESTripDetailsController 
{

    @Autowired private ESTripDetailsService service;
    @Autowired private ESDistressDetailsService esDistressDetailsService;
    @Autowired private ESRouteDeviationService esRouteDeviationService;
    @Autowired private ESDriverDutyDetailsService driverDutyDetailsService;
    

    @ApiOperation(value = "Get All trips From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("/getAllTrips")
    public Page<EsTripDetails> getAllDocs(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return service.getAllTrips(pageable);
    }

    @ApiOperation(value = "Search  Trip Details from Eleastic Search", response = EsTripDetails.class)
    @GetMapping("tripDetails/search_")
    public Page<EsTripDetails> searchTrip(@RequestParam(value = "searchValue" , required = false) String searchValue,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return service.getDocuments(searchValue, pageable);
    }

    @ApiOperation(value = "Search Trip Tracking From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("trackTrip/search_")
    public Page<ESTripTrackingDetails> searchTrack(@RequestParam(value = "searchValue" , required = false) String searchValue,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return service.searchTracks(searchValue, pageable);
    }

    @ApiOperation(value = "Get All Trip Tracking From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("/getAllTracks")
    public Page<ESTripTrackingDetails> getAllTracks(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return service.getAllTracks(pageable);
    }
    
    @ApiOperation(value = "Get Trip Tracking Based on TripId and trackId", response = EsTripDetails.class)
    @GetMapping("/getTracks")
    public List<ESTripTrackingDetails> findByTripIdAndTrackId(@QueryParam("tripId") Long tripId,@QueryParam("trackId") Long trackId) 
    {
    	return service.findByTripIdAndTrackId(tripId, trackId);
    }
    
    @ApiOperation(value = "Get All Live vehicles From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("/getLiveVehicles")
    public Page<ESTripTrackingDetails> getLiveVehicles(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return service.getLiveVehicles();
    }
    
    @ApiOperation(value = "Get All Distress Alerts From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("/getAllDistrssAlerts")
    public Page<ESDistressDetails> getAllDistrssAlerts(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return esDistressDetailsService.findAll(pageable);
    }

    @ApiOperation(value = "Search  Trip Details from Eleastic Search", response = EsTripDetails.class)
    @GetMapping("distress/search_")
    public Page<ESDistressDetails> searchDistress(@RequestParam(value = "searchValue" , required = false) String searchValue,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return esDistressDetailsService.searchDistress(searchValue, pageable);
    }
    
    @ApiOperation(value = "Get All Distress Alerts From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("/getAllDeviations")
    public Page<ESRouteDeviation> getAllDeviations(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return esRouteDeviationService.findAll(pageable);
    }

    @ApiOperation(value = "Search  Route Deviations from Eleastic Search", response = EsTripDetails.class)
    @GetMapping("deviations/search_")
    public Page<ESRouteDeviation> searchDeviations(@RequestParam(value = "searchValue" , required = false) String searchValue,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return esRouteDeviationService.searchDeviations(searchValue, pageable);
    }
    
    @ApiOperation(value = "Get All Distress Alerts From Eleastic Search", response = EsTripDetails.class)
    @GetMapping("/getAllDriverDuties")
    public Page<ESDriverDutyDetails> getAllDriverDuties(@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return driverDutyDetailsService.findAll(pageable);
    }

    @ApiOperation(value = "Search  Route Deviations from Eleastic Search", response = EsTripDetails.class)
    @GetMapping("driverDuties/search_")
    public Page<ESDriverDutyDetails> searchDuties(@RequestParam(value = "searchValue" , required = false) String searchValue,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) 
    {
    	return driverDutyDetailsService.searchDuties(searchValue, pageable);
    }
}
