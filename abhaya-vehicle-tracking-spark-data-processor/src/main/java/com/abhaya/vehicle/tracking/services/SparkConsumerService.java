package com.abhaya.vehicle.tracking.services;

import java.util.Arrays;
import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.config.KafkaConsumerConfig;
import com.abhaya.vehicle.tracking.processor.DataIngestionProcessor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SparkConsumerService 
{
    private final SparkConf sparkConf;
    private final KafkaConsumerConfig kafkaConsumerConfig;
    private final Collection<String> topics;
    
    @Autowired private DataIngestionProcessor processor;

    @Autowired
    public SparkConsumerService(SparkConf sparkConf,KafkaConsumerConfig kafkaConsumerConfig,@Value("${kafka.topic.dataprocessortopic}") String[] topics) 
    {
        this.sparkConf = sparkConf;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.topics = Arrays.asList(topics);
    }

    public void run()
    {
        log.info("Running Spark Consumer Service..");

        // Create context with a 10 seconds batch interval
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(jssc,LocationStrategies.PreferConsistent(),ConsumerStrategies.Subscribe(topics, kafkaConsumerConfig.consumerConfigs()));

        messages.foreachRDD(rdd -> 
        {
            rdd.foreach(record -> 
            {
            	log.info(record.value());
            	log.info("*********************************************************************");
            	processor.parse(record.value());
            });
        });
        jssc.start();
        try 
        {
            jssc.awaitTermination();
        } 
        catch (InterruptedException e) 
        {
        	log.info("Exception :: " + e.getCause(),e);
        }
    }
}