package com.abhaya.vehicle.tracking.service;

import com.abhaya.vehicle.tracking.entity.TotalTrafficData;
import com.abhaya.vehicle.tracking.vo.AggregateKey;
import com.abhaya.vehicle.tracking.vo.IoTData;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaMapWithStateDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.elasticsearch.spark.streaming.api.java.JavaEsSparkStreaming;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class IoTTrafficDataProcessor implements Serializable {
    private static final Logger logger = Logger.getLogger(IoTTrafficDataProcessor.class);

    /**
     * Method to get total traffic counts of different type of vehicles for each route.
     *
     * @param filteredIotDataStream IoT data stream
     */
    public void processTotalTrafficData(JavaDStream<IoTData> filteredIotDataStream) {

        // We need to get count of vehicle group by routeId and vehicleType
        JavaPairDStream<AggregateKey, Long> countDStreamPair = filteredIotDataStream
                .mapToPair(iot -> new Tuple2<>(new AggregateKey(iot.getRouteId(), iot.getVehicleType()), 1L))
                .reduceByKey((a, b) -> a + b);

        // Need to keep state for total count
        JavaMapWithStateDStream<AggregateKey, Long, Long, Tuple2<AggregateKey, Long>> countDStreamWithStatePair = countDStreamPair
                .mapWithState(StateSpec.function(totalSumFunc).timeout(Durations.seconds(3600)));//maintain state for one hour

        // Transform to dstream of TrafficData
        JavaDStream<Tuple2<AggregateKey, Long>> countDStream = countDStreamWithStatePair.map(tuple2 -> tuple2);
        JavaDStream<TotalTrafficData> trafficDStream = countDStream.map(totalTrafficDataFunc);

        // Map Cassandra table column
        Map<String, String> columnNameMappings = new HashMap<String, String>();
        columnNameMappings.put("routeId", "routeid");
        columnNameMappings.put("vehicleType", "vehicletype");
        columnNameMappings.put("totalCount", "totalcount");
        columnNameMappings.put("timeStamp", "timestamp");
        columnNameMappings.put("recordDate", "recorddate");
        JavaEsSparkStreaming.saveToEs(trafficDStream, "abhaya/_doc");

        trafficDStream.print();
    }


    //Function to get running sum by maintaining the state
    private static final Function3<AggregateKey, Optional<Long>, State<Long>, Tuple2<AggregateKey, Long>> totalSumFunc = (key, currentSum, state) -> {
        long totalSum = currentSum.or(0L) + (state.exists() ? state.get() : 0);
        Tuple2<AggregateKey, Long> total = new Tuple2<>(key, totalSum);
        state.update(totalSum);
        return total;
    };

    //Function to create TotalTrafficData object from IoT data
    private static final Function<Tuple2<AggregateKey, Long>, TotalTrafficData> totalTrafficDataFunc = (tuple -> {
        logger.debug("Total Count : " + "key " + tuple._1().getRouteId() + "-" + tuple._1().getVehicleType() + " value " + tuple._2());
        TotalTrafficData trafficData = new TotalTrafficData();
        trafficData.setRouteId(tuple._1().getRouteId());
        trafficData.setVehicleType(tuple._1().getVehicleType());
        trafficData.setTotalCount(tuple._2());
        trafficData.setTimeStamp(new Date());
        trafficData.setRecordDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return trafficData;
    });

}
