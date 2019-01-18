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

import com.abhaya.vehicle.tracking.assembler.StatusInfoResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadStatusInfoSetEvent;
import com.abhaya.vehicle.tracking.resource.StatusInfoResource;
import com.abhaya.vehicle.tracking.services.StatusInfoService;
import com.abhaya.vehicle.tracking.vos.VehicleStatusInfoVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/statusInfo")
public class StatusInfoQueryController 
{
	@Autowired private StatusInfoService service;
	@Autowired private StatusInfoResourceAssembler assembler;

	@ApiOperation(value = "View list of Vehicle Staus Info", response = ResponseEntity.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<StatusInfoResource>> readVehicleData(@RequestParam(value = "rcNumber", required = false) String rcNumber,
			@RequestParam(value = "rfId", required = false) String rfId,@RequestParam(value = "property", required = false) String property,
			@RequestParam(value = "serialNumber", required = false) String serialNumber,@RequestParam(value = "direction", required = false) String direction,
			@RequestParam(value = "dlNumber", required = false) String dlNumber,@RequestParam(value = "vehicleId", required = false) Long vehicleId,
			@RequestParam(value = "trackId", required = false) Long trackId,@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<VehicleStatusInfoVO> pagedAssembler) 
	{
		ReadStatusInfoSetEvent request = new ReadStatusInfoSetEvent();
		request.setPageable(pageable);
		request.setRcNumber(rcNumber);
		request.setSerialNumber(serialNumber);
		request.setRfId(rfId);
		request.setDlNumber(dlNumber);
		request.setVehicleId(vehicleId);
		request.setTrackId(trackId);

		PageReadEvent<VehicleStatusInfoVO> event = service.readData(request);
		Page<VehicleStatusInfoVO> page = event.getPage();
		PagedResources<StatusInfoResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
