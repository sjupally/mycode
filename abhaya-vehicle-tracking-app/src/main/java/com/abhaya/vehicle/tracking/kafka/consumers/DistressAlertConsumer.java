package com.abhaya.vehicle.tracking.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.DistressDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DistressAlertConsumer 
{
	@Autowired private DistressDetailsService service;
	@Autowired private SimpMessagingTemplate template;

	@KafkaListener(topics = "${kafka.topic.distress}")
	public void receive(String citizenContactNumber) 
	{
		String mobileNumber = citizenContactNumber.split(",")[0];
		String eventType = citizenContactNumber.split(",")[1];
		String isClosed = citizenContactNumber.split(",")[2];
		log.info("DistressAlertConsumer Received payload = '{}'", citizenContactNumber);
		
		if(isClosed.equalsIgnoreCase("false"))
			service.save(mobileNumber,eventType);
		else
			service.update(mobileNumber,eventType);
		
		
		//template.convertAndSend("/topic/messages", "A Distress alert is raised from Passenger with Mobile Number : " + mobileNumber + "- eventType :"+ eventType + " - isClosed :" + isClosed);
		template.convertAndSend("/topic/messages", "A Distress alert is raised from Passenger with Mobile Number : " + String.join(",", mobileNumber,eventType, isClosed) );
		service.sendSms(mobileNumber);
	}
}
