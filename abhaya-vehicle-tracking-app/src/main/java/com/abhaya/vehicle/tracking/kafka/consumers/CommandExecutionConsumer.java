package com.abhaya.vehicle.tracking.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.CommandExecutionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommandExecutionConsumer 
{
	@Autowired private CommandExecutionService service;

	@KafkaListener(topics = "${kafka.topic.commandexecution}")
	public void receive(String payload) throws Exception 
	{
		log.info("CommandExecutionConsumer Received payload = '{}'", payload);
		service.executeCommand(payload);
	}
}
