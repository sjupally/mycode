package com.abhaya.vehicle.tracking.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.TripDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EndTripConsumer 
{
	@Autowired private TripDetailsService service;

	@KafkaListener(topics = "${kafka.topic.endtrip}")
	public void receive(String source) throws Exception 
	{
		log.info("EndTripConsumer Received payload = '{}'", source);
		service.updateDocumentOnEndTrip(source);
	}
}

