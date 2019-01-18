package com.abhaya.vehicle.tracking.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.events.CreateRouteDeviationEvent;
import com.abhaya.vehicle.tracking.services.RouteDeviationService;
import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RouteDeviationConsumer 
{
	//@Autowired private RouteDeviationService routeDeviationService;
	@Autowired private RouteDeviationService service;

	@KafkaListener(topics = "${kafka.topic.deviation}")
	public void receive(String source) throws Exception 
	{
		log.info("RouteDeviationConsumer Received payload = '{}'", source);
		
		String citizenMobileNumber = source.split("#")[1];
		String location = source.split("#")[0];
		RouteDeviationVO routeDeviationVO=RouteDeviationVO.builder().citizenMobileNumber(citizenMobileNumber).location(location).build();
		CreateRouteDeviationEvent event = new CreateRouteDeviationEvent();
		event.setRouteDeviationVO(routeDeviationVO);
		service.save(event);
	   // routeDeviationService.sendSms(source);
	}
}
