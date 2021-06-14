package com.gabia.emailservice.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonEmailService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;


    public SendEmailResponse send(SendEmailRequest request) {
        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(request.toSendRequestDto());

        return SendEmailResponse.builder()
                .message(sendEmailResult.getMessageId())
                .build();
    }

    public SendEmailResponse sendVerifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        VerifyEmailIdentityResult verifyEmailIdentityResult = amazonSimpleEmailService.verifyEmailIdentity(request);

        return SendEmailResponse.builder()
                .message("ok")
                .build();
    }
}

