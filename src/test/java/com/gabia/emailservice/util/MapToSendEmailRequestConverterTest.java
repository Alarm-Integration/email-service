package com.gabia.emailservice.util;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class MapToSendEmailRequestConverterTest {

    private MapToSendEmailRequestConverter mapToSendEmailRequestConverter = new MapToSendEmailRequestConverter();

    @Test
    void convert_성공() {
        //given
        String sender = "sender@email.com";
        String title = "제목";
        String content = "내용";
        List<String> raws = Arrays.asList("receiver@email.com");
        String traceId = "abc";
        Long userId = 1L;

        HashMap<String, Object> data = new HashMap<>();
        data.put("sender",sender);
        data.put("title", title);
        data.put("content", content);
        data.put("raws", raws);
        data.put("traceId", traceId);
        data.put("userId", userId);

        //when
        SendEmailRequest sendEmailRequest = mapToSendEmailRequestConverter.convert(data);

        //then
        Assertions.assertThat(sendEmailRequest.getSender()).isEqualTo(sender);
        Assertions.assertThat(sendEmailRequest.getTitle()).isEqualTo(title);
        Assertions.assertThat(sendEmailRequest.getContent()).isEqualTo(content);
        Assertions.assertThat(sendEmailRequest.getTraceId()).isEqualTo(traceId);
        Assertions.assertThat(sendEmailRequest.getUserId()).isEqualTo(userId);
        Assertions.assertThat(sendEmailRequest.getRaws()).isEqualTo(raws);
    }
}