package com.abhaya.vehicle.tracking.kafka.consumers;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.TripDetailsService;
import com.abhaya.vehicle.tracking.vos.TripVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RouteIntiationConsumer 
{
	@Autowired private TripDetailsService service;

	@KafkaListener(topics = "${kafka.topic.routeintiation}")
	public void receive(String source) throws Exception 
	{
		log.info("RouteIntiationConsumer Received payload = '{}'", source);
		service.save(new ObjectMapper().readValue(source, TripVO.class));
	}
}
