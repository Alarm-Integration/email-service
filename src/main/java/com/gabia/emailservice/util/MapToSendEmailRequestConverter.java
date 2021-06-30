package com.gabia.emailservice.util;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MapToSendEmailRequestConverter implements Converter<HashMap<String, Object>, SendEmailRequest> {

    @Override
    public SendEmailRequest convert(HashMap<String, Object> source) {
        Long userId;

        if (source.get("userId") instanceof Integer) {
            userId = Long.valueOf((Integer) source.get("userId"));
        } else {
            userId = (Long) source.get("userId");
        }

        return SendEmailRequest.builder()
                .content((String) source.get("content"))
                .sender((String) source.get("sender"))
                .title((String) source.get("title"))
                .raws((List<String>) source.get("raws"))
                .traceId((String) source.get("traceId"))
                .userId(userId)
                .build();

    }
}
