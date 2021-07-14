package com.gabia.emailservice.sender;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AWSEmailSender implements CommonEmailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void sendEmail(SendEmailRequest request) throws Exception {
        try {
            amazonSimpleEmailService.sendEmail(request.toAWSRequest());
        } catch (AmazonSimpleEmailServiceException e) {
            throw new Exception(String.format("errorCode: %s", e.getErrorCode()));
        }
    }

    @Override
    public void sendVerifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        amazonSimpleEmailService.verifyEmailIdentity(request);
        log.info("AWSEmailSender: 인증 메일 발송 성공");
    }
}

