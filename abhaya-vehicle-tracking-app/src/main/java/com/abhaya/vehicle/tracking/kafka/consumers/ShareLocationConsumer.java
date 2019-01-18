package com.abhaya.vehicle.tracking.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.ShareLocationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ShareLocationConsumer 
{
	@Autowired private ShareLocationService service;

	@KafkaListener(topics = "${kafka.topic.sharelocation}")
	public void receive(String source) throws Exception 
	{
		log.info("ShareLocationConsumer Received payload = '{}'", source);
		service.shareLocation(source);
	}
}
