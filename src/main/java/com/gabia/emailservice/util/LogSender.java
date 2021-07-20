package com.gabia.emailservice.util;

import org.komamitsu.fluency.Fluency;
import org.komamitsu.fluency.fluentd.FluencyBuilderForFluentd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogSender {

    @Value("${fluentd.uri}")
    private String fluentdServerHost;

    @Value("${fluentd.port}")
    private int fluentdServerPort;

    public void sendAlarmResults(String appName, String requestId, String logMessage, boolean isSuccess, String address) throws IOException {
        String tag = "alarm.result.access";

        Map<String, Object> event = new HashMap<>();
        event.put("app_name", appName);
        event.put("request_id", requestId);
        event.put("log_message", logMessage);
        event.put("is_success", isSuccess);
        event.put("address", address);

        Fluency fluency = new FluencyBuilderForFluentd().build(fluentdServerHost, fluentdServerPort);
        fluency.emit(tag, event);
        fluency.close();
    }
}