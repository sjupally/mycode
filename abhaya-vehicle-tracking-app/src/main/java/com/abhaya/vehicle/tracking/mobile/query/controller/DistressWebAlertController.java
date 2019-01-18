package com.abhaya.vehicle.tracking.mobile.query.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DistressWebAlertController 
{
	
	@MessageMapping("/distress")
	@SendTo("/topic/messages")
	public String handle(String message) throws Exception 
	{
	    return message;
	}
}
