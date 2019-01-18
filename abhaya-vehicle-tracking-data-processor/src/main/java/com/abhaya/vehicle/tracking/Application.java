package com.abhaya.vehicle.tracking;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.abhaya.vehicle.tracking.kafka.verticals.MainVertical;

@ComponentScan(basePackages = "com.abhaya")
@EnableAutoConfiguration
@SpringBootApplication
@Configuration
public class Application 
{
	
	@Value("${tcp.host.port}")
	private int port;

	@Autowired
	private MainVertical mainVertical;
	
	
	public static void main(String[] args) throws Exception 
	{
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void deployVerticle() throws Exception 
	{
		
		mainVertical.start();
		/*VertxOptions options = new VertxOptions();
		options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		Vertx.vertx(options).deployVerticle(tcpServer);*/
	}
}
