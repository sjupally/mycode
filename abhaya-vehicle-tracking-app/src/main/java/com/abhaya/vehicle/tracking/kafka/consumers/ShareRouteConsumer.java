package com.abhaya.vehicle.tracking.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.TripDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ShareRouteConsumer 
{
	@Autowired private TripDetailsService service;

	@KafkaListener(topics = "${kafka.topic.shareroute}")
	public void receive(String source) throws Exception 
	{
		log.info("ShareRouteConsumer Received payload = '{}'", source);
		service.shareMyRoute(source);
	}
}
