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

    @KafkaListener(topics = "email", groupId = "email", containerFactory = "emailListenerContainerFactory")
    public void sendEmail(SendEmailRequest sendEmailRequest) {
        try {
            commonEmailSender.sendEmail(sendEmailRequest);
            log.info("EmailService: 메일 발송 완료");
        } catch (Exception e) {
            log.error("EmailService: 메일 발송 실패 {}", e.getMessage());
        }
    }

    public void sendVerifyEmail(String emailAddress) {
        commonEmailSender.sendVerifyEmail(emailAddress);
        log.info("EmailService: 인증 메일 발송 완료");
    }
}
