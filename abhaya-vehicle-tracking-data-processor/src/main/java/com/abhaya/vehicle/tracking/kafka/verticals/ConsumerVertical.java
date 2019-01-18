package com.abhaya.vehicle.tracking.kafka.verticals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.processor.DataIngestionProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.vertx.core.AbstractVerticle;
import io.vertx.kafka.client.common.TopicPartition;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConsumerVertical extends AbstractVerticle 
{
	@Autowired DataIngestionProcessor dataIngestionProcessor;
	@Autowired KafkaProperties kafkaProperties ;
	
	@Override
	public void start() throws Exception
	{
		
		KafkaConsumer<String, String> consumer = kafkaProperties.createKafkaConsumer(vertx);
		TopicPartition tp = new TopicPartition().setPartition(0).setTopic("abhaya-topic");
		consumer.seekToEnd(tp);
		consumer.handler(record -> 
		{
			log.info("value = " + record.value());
			try 
			{
				dataIngestionProcessor.parse(record.value());
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		consumer.assign(tp, ar -> 
		{
			if (ar.succeeded()) 
			{
				log.info("subscribed to "+ar);
			} 
			else 
			{
				log.info("Could not subscribe " + ar.cause().getMessage());
			}
		});
		consumer.exceptionHandler(e -> 
		{
			log.info("Error = " + e.getMessage());
		});
	}
}
