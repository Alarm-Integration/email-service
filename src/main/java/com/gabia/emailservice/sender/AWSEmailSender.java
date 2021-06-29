package com.gabia.emailservice.sender;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
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
            SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(request.toAWSRequest());
            log.info(String.format("AWSEmailSender: 발송 성공 %s", sendEmailResult.getMessageId()));
        } catch (Exception e) {
            if (e.getMessage().startsWith("Email address is not verified")) {
                log.error(String.format("AWSEmailSender: 인증된 발신자(%s)가 아닙니다", request.getSender()));
                throw new Exception("인증된 발신자가 아닙니다");
            }
            log.error(String.format("AWSEmailSender: %s", e.getMessage()));
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void sendVerifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        amazonSimpleEmailService.verifyEmailIdentity(request);
        log.info("AWSEmailSender: 인증 메일 발송 성공");
    }
}

