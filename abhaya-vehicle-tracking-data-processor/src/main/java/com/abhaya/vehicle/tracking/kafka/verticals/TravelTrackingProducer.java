package com.abhaya.vehicle.tracking.kafka.verticals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TravelTrackingProducer 
{
	/*@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${kafka.group.groupId}")
	private String groupId;
	
	public void send(String data) throws Exception
	{
		
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.ACKS_CONFIG, "1");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		producer.send(new ProducerRecord<String, String>("travel_tracking", data));
	    producer.close();
	}*/
	

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	public void send(String payload) 
	{
		kafkaTemplate.send("travel-tracking", payload);
	}
}
