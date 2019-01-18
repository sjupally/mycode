package com.abhaya.vehicle.tracking;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
public class Application 
{
    public static void main(String[] args) throws Exception 
    {
    	log.debug("Starting abhaya-vehicle-tracking-jobs-scheduler");
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
		log.debug("abhaya-vehicle-tracking-jobs-scheduler application name: {}", ctx.getApplicationName());
		log.debug("Active profiles: " + Arrays.toString(ctx.getEnvironment().getActiveProfiles()));
  }
    
    
}
