package com.abhaya.vehicle.tracking.query.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhaya.vehicle.tracking.components.ComboComponent;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/comboData")
public class ComponentQueryController
{
	@Autowired
	private ApplicationContext appContext;
	
	@ApiOperation(value = "View Data for Combo", response = ResponseEntity.class)
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComboDataVO> getData4Combo(HttpServletRequest request)
    {
        ComboComponent comboComponent = (ComboComponent) appContext.getBean(request.getParameter("actionType"));
        return comboComponent.getData4Combo(request.getParameter("extraParams"));
    }
}