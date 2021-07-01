package com.gabia.emailservice.service;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.sender.CommonEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final CommonEmailSender commonEmailSender;

    @KafkaListener(topics = "email", groupId = "email", containerFactory = "kafkaListenerContainerFactory")
    public void sendEmail(SendEmailRequest request) {
        try {
            commonEmailSender.sendEmail(request);
            log.info("{}: userId:{} traceId:{} massage:{}", getClass().getSimpleName(), request.getUserId(), request.getTraceId(), "메일 발송 성공");
        } catch (Exception e) {
            log.error("{}: userId:{} traceId:{} massage:{}", getClass().getSimpleName(), request.getUserId(), request.getTraceId(), "메일 발송 실패");
        }
    }

    public void sendVerifyEmail(String emailAddress) {
        commonEmailSender.sendVerifyEmail(emailAddress);
        log.info("EmailService: 인증 메일 발송 완료");
    }
}
