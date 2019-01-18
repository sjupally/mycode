package com.abhaya.vehicle.tracking.kafka.verticals;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.producer.KafkaProducer;

@Component
public class KafkaProperties 
{

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${kafka.group.groupId}")
	private String groupId;
	
	public  KafkaConsumer<String, String>  createKafkaConsumer(Vertx vertx)
	{
		Properties config = new Properties();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		
		return KafkaConsumer.create(vertx, config);
	}
	
	public  KafkaProducer<String, String>  createKafkaProducer(Vertx vertx)
	{
		Properties config = new Properties();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ProducerConfig.ACKS_CONFIG, "1");
		
		return KafkaProducer.create(vertx, config,String.class,String.class);
	}
}
