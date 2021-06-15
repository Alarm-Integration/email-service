package com.gabia.emailservice.sender;

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
public class AmazonEmailSender implements CommonEmailSender {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public SendEmailResponse sendEmail(SendEmailRequest request) {
        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(request.toSendRequestDto());

        return SendEmailResponse.builder()
                .message(sendEmailResult.getMessageId())
                .build();
    }

    @Override
    public SendEmailResponse sendVerifyEmail(String emailAddress) {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        VerifyEmailIdentityResult verifyEmailIdentityResult = amazonSimpleEmailService.verifyEmailIdentity(request);

        return SendEmailResponse.builder()
                .message("ok")
                .build();
    }
}

