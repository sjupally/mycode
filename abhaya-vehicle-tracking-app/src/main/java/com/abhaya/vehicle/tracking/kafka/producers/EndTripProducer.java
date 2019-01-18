package com.abhaya.vehicle.tracking.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EndTripProducer
{
	@Value("${kafka.topic.endtrip}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String payload) 
	{
		log.info("End Trip  Topic :: " + topic + " End Trip  Pay load :: " + payload);
		kafkaTemplate.send(topic, payload);
	}
}
