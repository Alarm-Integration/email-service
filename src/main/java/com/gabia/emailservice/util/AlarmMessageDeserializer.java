package com.gabia.emailservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.emailservice.dto.request.AlarmMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class AlarmMessageDeserializer implements Deserializer<AlarmMessage> {

    @Override
    public AlarmMessage deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        AlarmMessage alarmMessage = null;

        try {
            alarmMessage = mapper.readValue(data, AlarmMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return alarmMessage;
    }
}
