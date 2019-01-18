package com.abhaya.vehicle.tracking.kafka.verticals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

@Component
public class MainVertical extends AbstractVerticle
{
	
	@Autowired private ProducerVertical producerVertical;
	@Autowired private ConsumerVertical consumerVertical;

	@Override
	public void start() throws Exception 
	{
		VertxOptions options = new VertxOptions();
		options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		Vertx.vertx(options).deployVerticle(producerVertical);
		Vertx.vertx(options).deployVerticle(consumerVertical);
	}
}
