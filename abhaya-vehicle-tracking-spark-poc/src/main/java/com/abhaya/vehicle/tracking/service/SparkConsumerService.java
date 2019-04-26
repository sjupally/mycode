package com.abhaya.vehicle.tracking.service;

import com.abhaya.vehicle.tracking.config.KafkaConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class SparkConsumerService {
    private final KafkaConsumerConfig kafkaConsumerConfig;
    private final Collection<String> topics;
    private Map<String, String> cfg = new HashMap<>();


    //private EsMajorVersion version = TestUtils.getEsClusterInfo().getMajorVersion();
    @Autowired
    public SparkConsumerService(KafkaConsumerConfig kafkaConsumerConfig, @Value("${kafka.topic.dataprocessortopic}") String[] topics) {
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.topics = Arrays.asList(topics);
    }

    public void run() {
        log.info("Running Spark Consumer Service.." + topics);
        /*//System.setProperty("hadoop.home.dir", "/usr/local/hadoop");
        Map<String, String> localConf = new HashMap<>(cfg);
        localConf.put(ES_INDEX_AUTO_CREATE, "no");
        // Create context with a 10 seconds batch interval
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        // Start reading messages from Kafka and get DStream
        final JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaConsumerConfig.consumerConfigs()));
        JavaDStream<String> lines = stream.map(record -> {
            System.out.println("#############" + record.value());
            return record.value();
        });
        System.out.println("*************************" + lines);




        JavaEsSparkStreaming.saveJsonToEs(lines, "abhaya/_doc");
        //String target = wrapIndex(resource("packet", "data", version));
        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (Exception e) {
            log.info("Exception :: " + e.getCause(), e);
        }*/
    }

   
}
