package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

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

import com.abhaya.vehicle.tracking.assembler.DeviceCommunicationResourceAssembler;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDeviceCommunicationEvent;
import com.abhaya.vehicle.tracking.resource.DeviceCommunicationResource;
import com.abhaya.vehicle.tracking.services.DeviceCommunicationService;
import com.abhaya.vehicle.tracking.utils.DeviceCommunicationSummaryVO;
import com.abhaya.vehicle.tracking.vos.DeviceCommunicationVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/deviceCommunication")
public class DeviceCommunicationQueryController {

    @Autowired
    private DeviceCommunicationService service;
    @Autowired
    private DeviceCommunicationResourceAssembler assembler;

    @ApiOperation(value = "View Device Communication Data", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<DeviceCommunicationResource>> readData(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "districtId", required = false) Long districtId,
            @RequestParam(value = "cityId", required = false) Long cityId,
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "searchDate", required = false) String searchDate,
            @RequestParam(value = "movement", required = false) String movement,
            @RequestParam(value = "batteryStatus", required = false) String batteryStatus,
            @RequestParam(value = "ignitionStatus", required = false) String ignitionStatus,
            @RequestParam(value = "engineStatus", required = false) String engineStatus,
            @RequestParam(value = "tamperStatus", required = false) String tamperStatus,
            @RequestParam(value = "panicButtonStatus", required = false) String panicButtonStatus,
            @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable, PagedResourcesAssembler<DeviceCommunicationVO> pagedAssembler) {
        ReadDeviceCommunicationEvent request = new ReadDeviceCommunicationEvent();
        request.setPageable(pageable);
        request.setStatus(status);
        request.setStateId(stateId);
        request.setDistrictId(districtId);
        request.setCityId(cityId);
        request.setSearchValue(searchValue);
        request.setSearchDate(searchDate);
        request.setMovement(movement);
        request.setBatteryStatus(batteryStatus);
        request.setIgnitionStatus(ignitionStatus);
        request.setEngineStatus(engineStatus);
        request.setTamperStatus(tamperStatus);
        request.setPanicButtonStatus(panicButtonStatus);

        PageReadEvent<DeviceCommunicationVO> event = service.readData(request);
        Page<DeviceCommunicationVO> page = event.getPage();
        PagedResources<DeviceCommunicationResource> pagedResources = pagedAssembler.toResource(page, assembler);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }

    @ApiOperation(value = "View Device Communication Status Summary", response = DeviceCommunicationSummaryVO.class)
    @RequestMapping(value = "statusSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceCommunicationSummaryVO> getDeviceCommunicationSummary(
            @RequestParam(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "districtId", required = false) Long districtId,
            @RequestParam(value = "cityId", required = false) Long cityId,
            @RequestParam(value = "searchDate", required = false) String searchDate,
            @RequestParam(value = "isDistrictWise", required = false, defaultValue = "false") Boolean isDistrictWise) {
        ReadDeviceCommunicationEvent request = new ReadDeviceCommunicationEvent();
        request.setStateId(stateId);
        request.setDistrictId(districtId);
        request.setCityId(cityId);
        request.setSearchDate(searchDate);
        request.setIsDistrictWise(isDistrictWise);
        List<DeviceCommunicationSummaryVO> list = service.getDeviceCommunicationSummary(request);
        return list;
    }

    @ApiOperation(value = "View Device Communication Movement Summary", response = DeviceCommunicationSummaryVO.class)
    @RequestMapping(value = "movementSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceCommunicationSummaryVO> getDeviceCommunicationMovementSummary(
            @RequestParam(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "districtId", required = false) Long districtId,
            @RequestParam(value = "cityId", required = false) Long cityId,
            @RequestParam(value = "searchDate", required = false) String searchDate,
            @RequestParam(value = "isDistrictWise", required = false, defaultValue = "false") Boolean isDistrictWise) {
        ReadDeviceCommunicationEvent request = new ReadDeviceCommunicationEvent();
        request.setStateId(stateId);
        request.setDistrictId(districtId);
        request.setCityId(cityId);
        request.setSearchDate(searchDate);
        request.setIsDistrictWise(isDistrictWise);
        List<DeviceCommunicationSummaryVO> list = service.getDeviceCommunicationMovementSummary(request);
        return list;
    }
}