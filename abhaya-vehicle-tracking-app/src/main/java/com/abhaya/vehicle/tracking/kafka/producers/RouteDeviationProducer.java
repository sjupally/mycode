package com.abhaya.vehicle.tracking.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RouteDeviationProducer 
{
	@Value("${kafka.topic.deviation}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String payload) 
	{
		log.info("Deviation Topic :: " + topic + " Deviation Pay load :: " + payload);
		kafkaTemplate.send(topic, payload);
	}
}
