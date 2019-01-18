package com.abhaya.vehicle.tracking.query.controller;

import com.abhaya.vehicle.tracking.events.ReadVehicleStatusInfoReportEvent;
import com.abhaya.vehicle.tracking.services.VehicleStatusDetailsService;
import com.abhaya.vehicle.tracking.utils.DriverAuthVO;
import com.abhaya.vehicle.tracking.utils.VehicleStatusVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
public class VehicleStatusReportQueryController {

    @Autowired
    private VehicleStatusDetailsService vehicleStatusDetailsService;


    @ApiOperation(value = "View vehicle status infos", response = DriverAuthVO.class)
    @RequestMapping(value = "getVehicleStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleStatusVO> getVehicleStatuses(
            @RequestParam(value = "cityId", required = false) Long cityId,
            @RequestParam(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "districtId", required = false) Long districtId,
            @RequestParam(value = "packetDate", required = false) String packetDate) {
        ReadVehicleStatusInfoReportEvent request = new ReadVehicleStatusInfoReportEvent();
        request.setCityId(cityId);
        request.setStateId(stateId);
        request.setDistrictId(districtId);
        request.setDate(packetDate);
        List<VehicleStatusVO> list = vehicleStatusDetailsService.readVehicleStatusInfo(request);
        return list;
    }
}
