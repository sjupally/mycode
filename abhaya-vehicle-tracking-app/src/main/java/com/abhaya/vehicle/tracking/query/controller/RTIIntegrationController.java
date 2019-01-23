package com.abhaya.vehicle.tracking.query.controller;

import com.abhaya.vehicle.tracking.services.RTIIntegrationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class RTIIntegrationController {

    @Autowired
    private RTIIntegrationService service;

    @ApiOperation(value = "Get DL Details", response = ResponseEntity.class)
    @RequestMapping(value = "getDlDetailsWithDlNo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDlDetailsWithDlNo(@RequestParam(value = "dlNo", required = false) String dlNo) {
        String response =  service.getDlDetailsWithDlNo(dlNo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
