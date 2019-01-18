package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
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

import com.abhaya.vehicle.tracking.assembler.VehicleDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.assembler.VehicleDriverDetailsResourceAssembler;
import com.abhaya.vehicle.tracking.events.EntityReadEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDutyDetailsSetEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDataEvent;
import com.abhaya.vehicle.tracking.events.ReadVehicleDetailsSetEvent;
import com.abhaya.vehicle.tracking.resource.VehicleDetailsResource;
import com.abhaya.vehicle.tracking.resource.VehicleDriverDetailsResource;
import com.abhaya.vehicle.tracking.services.VehicleDetailsService;
import com.abhaya.vehicle.tracking.services.VehicleDriverDetailsService;
import com.abhaya.vehicle.tracking.vos.StateWiseVehicleVO;
import com.abhaya.vehicle.tracking.vos.VehicleDetailsVO;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class VehicleDetailsQueryController 
{
	@Autowired private VehicleDetailsService service;
	@Autowired private VehicleDriverDetailsService vehicleDriverDetailsService;
	@Autowired private VehicleDetailsResourceAssembler assembler;
	@Autowired private VehicleDriverDetailsResourceAssembler assembler2;

	@ApiOperation(value = "View list of Vehicles", response = ResponseEntity.class)
	@RequestMapping(value="vehicleDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<VehicleDetailsResource>> readVehicleData(
			@RequestParam(value = "mobileNumber", required = false) Long mobileNumber,
			@RequestParam(value = "rcNumber", required = false) String rcNumber,
			@RequestParam(value = "property", required = false) String property,
			@RequestParam(value = "serialNumber", required = false) String serialNumber,
			@RequestParam(value = "direction", required = false) String direction,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "isDeviceMapped", required = false) Boolean isDeviceMapped,
			@RequestParam(value = "districtId", required = false) Long districtId,
			@RequestParam(value = "cityId", required = false) Long cityId,
			@RequestParam(value = "date", required = false) String date,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<VehicleDetailsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
	{
		ReadVehicleDetailsSetEvent request = new ReadVehicleDetailsSetEvent();
		request.setPageable(pageable);
		request.setMobileNumber(mobileNumber);
		request.setRcNumber(rcNumber);
		request.setSerialNumber(serialNumber);
		request.setSearchValue(searchValue);
		request.setIsDeviceMapped(isDeviceMapped);
		request.setDistrictId(districtId);
		request.setCityId(cityId);
		request.setDate(date);
		
		PageReadEvent<VehicleDetailsVO> event = service.readVehicleData(request);
		Page<VehicleDetailsVO> page = event.getPage();
		PagedResources<VehicleDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View Single Vehicles", response = ResponseEntity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleDetailsResource> readDataById(@PathVariable Long id) 
	{
		ReadVehicleDataEvent request = new ReadVehicleDataEvent().setId(id);
		EntityReadEvent<VehicleDetailsVO> event = service.readDataById(request);
		if (!event.isFound()) 
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		VehicleDetailsVO entity = event.getEntity();
		return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
	}

	@ApiOperation(value = "View list of vehicle and Driver Details for Mobile", response = ResponseEntity.class)
	@RequestMapping(value="vehicleDriverDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<VehicleDriverDetailsResource>> vehicleDriverDetails(@RequestParam(value = "vehicleNumber", required = true) String vehicleNumber,
			HttpServletRequest httpServletRequest,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<VehicleDriverDetailsVO> pagedAssembler) 
	{
		ReadDriverDutyDetailsSetEvent request = new ReadDriverDutyDetailsSetEvent();
		request.setRcNumber(vehicleNumber);
		request.setPageable(pageable);
		
		PageReadEvent<VehicleDriverDetailsVO> event = vehicleDriverDetailsService.readVehicleDriverData(request);
		Page<VehicleDriverDetailsVO> page = event.getPage();
		PagedResources<VehicleDriverDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler2);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	
	@RequestMapping(value="getVehicleAnalytics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StateWiseVehicleVO> liveData(@RequestParam(value = "stateId", required = false) Long stateId) 
	{
		return service.getStateWideAnalytics(stateId);
	}
}