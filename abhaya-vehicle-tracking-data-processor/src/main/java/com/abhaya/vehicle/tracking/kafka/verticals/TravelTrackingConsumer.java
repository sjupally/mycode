package com.abhaya.vehicle.tracking.kafka.verticals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.services.GPSDataIngestionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TravelTrackingConsumer
{

	/*@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${kafka.group.groupId}")
	private String groupId;
	
	public void recieve() throws Exception
	{
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(""));
		      while (true) 
		      {
		    	 ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
		         for (ConsumerRecord<String, String> record : consumerRecords)
		         {
		        	 String data  = record.value();
		         }
		   }
	}*/
	
	@Autowired private GPSDataIngestionService gpsDataIngestionService;

	@KafkaListener(topics = "travel-tracking")
	public void receive(String payload) throws Exception 
	{
		gpsDataIngestionService.indexTravelTracking(payload);
	}
}
