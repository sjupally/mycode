package com.abhaya.vehicle.tracking.query.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.assembler.DistressDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.EntityReadEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDistressDetailsSetEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDataEvent;
import com.abhaya.vehicle.tracking.resource.DistressDetailsResource;
import com.abhaya.vehicle.tracking.services.DistressDetailsService;
import com.abhaya.vehicle.tracking.vos.DistressDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/distress")
public class DistressDetailsQueryController 
{

	@Autowired private DistressDetailsService service;
	@Autowired private DistressDetailsResourceAssembler assembler;
	
	@ApiOperation(value = "View list of Distress Alerts", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<DistressDetailsResource>> readUsersData(
			@RequestParam(value = "citizenMobileNumber", required = false) String citizenMobileNumber,
			@RequestParam(value = "dlNumber", required = false) String dlNumber,
			@RequestParam(value = "rcNumber", required = false) String rcNumber,
			@RequestParam(value = "serialNumber", required = false) String serialNumber,
			@RequestParam(value = "isClosed", required = false) Boolean isClosed,
			@RequestParam(value = "eventType", required = false) String eventType,
			@RequestParam(value = "stateId" , required = false) Long stateId,
			@RequestParam(value = "districtId" , required = false) Long districtId,
			@RequestParam(value = "cityId" , required = false) Long cityId,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@RequestParam(value = "tripId", required = false) Long tripId,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<DistressDetailsVO> pagedAssembler) 
	{
		ReadDistressDetailsSetEvent request = new ReadDistressDetailsSetEvent();
		request.setPageable(pageable);
		request.setCitizenMobileNumber(citizenMobileNumber);
		request.setDlNumber(dlNumber);
		request.setRcNumber(rcNumber);
		request.setSerialNumber(serialNumber);
		request.setIsClosed(isClosed);
		request.setEventType(eventType);
		request.setStateId(stateId);
		request.setDistrictId(districtId);
		request.setCityId(cityId);
		request.setSearchValue(searchValue);
		request.setSearchDate(searchDate);
		request.setTripId(tripId);
		
		PageReadEvent<DistressDetailsVO> event = service.readData(request);
		Page<DistressDetailsVO> page = event.getPage();
		PagedResources<DistressDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	@ApiOperation(value = "View  Distress By Id", response = ResponseEntity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DistressDetailsResource> readDataById(@PathVariable Long id) 
	{
		ReadVehicleDataEvent request = new ReadVehicleDataEvent().setId(id);
		EntityReadEvent<DistressDetailsVO> event = service.readDataById(request);
		if (!event.isFound()) 
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		DistressDetailsVO entity = event.getEntity();
		return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update Distress Status", response = ResponseEntity.class)
	@RequestMapping(value= "updateDistressStatus" ,method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDistressStatus(@RequestParam(value = "distressId" ,required = true) Long distressId,
			HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		service.updateDistressStatus(distressId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}