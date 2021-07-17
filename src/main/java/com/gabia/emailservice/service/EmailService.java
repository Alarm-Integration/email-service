package com.gabia.emailservice.service;

import com.gabia.emailservice.dto.request.AlarmMessage;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.sender.CommonEmailSender;
import com.gabia.emailservice.util.LogSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;

@Validated
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final CommonEmailSender commonEmailSender;
    private final LogSender logSender;

    @KafkaListener(topics = "email", groupId = "email", containerFactory = "kafkaListenerContainerFactory")
    public void sendEmail(AlarmMessage alarmMessage) throws IOException {
        String senderAddress = getSenderAddress(alarmMessage.getGroupId());
        SendEmailRequest sendEmailRequest = SendEmailRequest.createFrom(alarmMessage, senderAddress);

        try {
            commonEmailSender.sendEmail(sendEmailRequest);
            logSender.send(sendEmailRequest.getUserId(), "email", sendEmailRequest.getTraceId(), "메일 발송 성공");
            log.info("[{}: userId:{} traceId:{}] {}",
                    getClass().getSimpleName(), alarmMessage.getUserId(), alarmMessage.getTraceId(), "메일 발송 성공");
        } catch (Exception e) {
            logSender.send(sendEmailRequest.getUserId(), "email", sendEmailRequest.getTraceId(), "메일 발송 실패");
            log.error("[{}: userId:{} traceId:{}] {}",
                    getClass().getSimpleName(), alarmMessage.getUserId(), alarmMessage.getTraceId(), e.getMessage());
        }
    }

    public void sendVerifyEmail(String emailAddress) {
        commonEmailSender.sendVerifyEmail(emailAddress);
        log.info("EmailService: 인증 메일 발송 완료");
    }

    private String getSenderAddress(Long groupId) {
        //todo: 그룹 서비스에서 groupId로 대표 이메일 발신 주소 가져오기
        return "nameks17@gmail.com";
    }
}
