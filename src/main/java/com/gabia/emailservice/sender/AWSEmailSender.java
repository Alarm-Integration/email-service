package com.gabia.emailservice.sender;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.util.LogSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AWSEmailSender implements CommonEmailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final LogSender logSender;

    @Override
    public boolean sendEmail(SendEmailRequest request){
        try {
            amazonSimpleEmailService.sendEmail(request.toAWSRequest());

            request.getAddresses().forEach(address -> {
                try {
                    logSender.sendAlarmResults("email", request.getTraceId(), "메일 발송 성공", true, address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            return true;
        } catch (AmazonSimpleEmailServiceException e) {
            log.error("{}: userId:{} traceId:{} errorCode:{} message:{}",
                    getClass().getSimpleName(), request.getUserId(), request.getTraceId(), e.getErrorCode(), e.getMessage());

            request.getAddresses().forEach(address -> {
                try {
                    logSender.sendAlarmResults("email", request.getTraceId(), e.getErrorCode(), false, address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            return false;
        }
    }

    @Override
    public void sendVerifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        amazonSimpleEmailService.verifyEmailIdentity(request);
        log.info("AWSEmailSender: 인증 메일 발송 성공");
    }
}

