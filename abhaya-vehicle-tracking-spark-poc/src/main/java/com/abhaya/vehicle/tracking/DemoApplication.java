package com.abhaya.vehicle.tracking;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abhaya.vehicle.tracking.service.SparkDataProcessor;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	SparkDataProcessor service;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void run() throws Exception
	{
		service.run();
	}
}
