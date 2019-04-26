package com.abhaya.vehicle.tracking.service;

import com.abhaya.vehicle.tracking.config.KafkaConsumerConfig;
import com.abhaya.vehicle.tracking.util.IoTDataDecoder;
import com.abhaya.vehicle.tracking.vo.IoTData;
import com.google.common.base.Optional;
import kafka.serializer.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.*;


@Service
@Slf4j
public class SparkDataProcessor {

	private final KafkaConsumerConfig kafkaConsumerConfig;
	private final Set<String> set = new HashSet<String>();
	private Map<String, String> cfg = new HashMap<>();
	@Autowired
	IoTTrafficDataProcessor ioTTrafficDataProcessor;
	// private EsMajorVersion version =
	// TestUtils.getEsClusterInfo().getMajorVersion();
	@Autowired
	public SparkDataProcessor(KafkaConsumerConfig kafkaConsumerConfig,
			@Value("${kafka.topic.dataprocessortopic}") String[] topics) {
		this.kafkaConsumerConfig = kafkaConsumerConfig;
		set.addAll(Arrays.asList(topics));
	}

	public void run() {
		log.info("Running SparkDataProcessor..." + set);
		SparkConf sparkConf = new SparkConf()
                .setAppName("Iot Data Processor")
                .setMaster("local[4]");

		// Create context with a 10 seconds batch interval
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));
		jssc.checkpoint("C:\\Users\\srikanth.jupally\\spark-checkpoint");
		// create direct kafka stream
		JavaPairInputDStream<String, IoTData> directKafkaStream = KafkaUtils.createDirectStream(jssc, String.class,
				IoTData.class, StringDecoder.class, IoTDataDecoder.class, kafkaConsumerConfig.consumerConfigs(), set);

		// We need non filtered stream for poi traffic data calculation
		JavaDStream<IoTData> nonFilteredIotDataStream = directKafkaStream.map(tuple -> tuple._2());
		// We need filtered stream for total and traffic data calculation
		JavaPairDStream<String, IoTData> iotDataPairStream = nonFilteredIotDataStream.mapToPair(iot ->
				new Tuple2<String, IoTData>(iot.getVehicleId(), iot)).reduceByKey((a, b) -> a);

		// Check vehicle Id is already processed
		JavaMapWithStateDStream<String, IoTData, Boolean, Tuple2<IoTData, Boolean>> iotDStreamWithStatePairs = iotDataPairStream
				.mapWithState(StateSpec.function(processedVehicleFunc).timeout(Durations.seconds(3600)));// maintain
		// state for
		// one hour

		// Filter processed vehicle ids and keep un-processed
		JavaDStream<Tuple2<IoTData, Boolean>> filteredIotDStreams = iotDStreamWithStatePairs.map(tuple2 -> tuple2)
				.filter(tuple -> tuple._2.equals(Boolean.FALSE));

		// Get stream of IoTdata
		JavaDStream<IoTData> filteredIotDataStream = filteredIotDStreams.map(tuple -> tuple._1);
//		JavaEsSparkStreaming.saveToEs(filteredIotDataStream, "abhaya/_doc");
		// cache stream as it is used in total and window based computation
		filteredIotDataStream.cache();

		// process data
		ioTTrafficDataProcessor.processTotalTrafficData(filteredIotDataStream);

		// start context
		jssc.start();
		jssc.awaitTermination();
	}

	// Funtion to check processed vehicles.
	private static final Function3<String, Optional<IoTData>, State<Boolean>, Tuple2<IoTData, Boolean>> processedVehicleFunc = (
			String, iot, state) -> {
		Tuple2<IoTData, Boolean> vehicle = new Tuple2<>(iot.get(), false);
		if (state.exists()) {
			vehicle = new Tuple2<>(iot.get(), true);
		} else {
			state.update(Boolean.TRUE);
		}
		return vehicle;
	};

}
