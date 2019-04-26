package com.abhaya.vehicle.tracking.util;

import com.abhaya.vehicle.tracking.vo.IoTData;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

public class IoTDataDecoder implements Decoder<IoTData>  {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public IoTDataDecoder(VerifiableProperties verifiableProperties) {

    }
    public IoTData fromBytes(byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, IoTData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}