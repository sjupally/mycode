package com.abhaya.vehicle.tracking;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abhaya.vehicle.tracking.services.SparkConsumerService;


@SpringBootApplication
public class Application 
{
	@Autowired SparkConsumerService service;
    public static void main(String[] args) throws Exception 
    {
        SpringApplication.run(Application.class, args);
    }
    
    @PostConstruct
	public void run() throws Exception 
	{
    	service.run();
	}
}
