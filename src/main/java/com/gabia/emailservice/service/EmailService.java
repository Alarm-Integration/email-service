package com.gabia.emailservice.service;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import com.gabia.emailservice.sender.CommonEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final CommonEmailSender commonEmailSender;

    public SendEmailResponse sendEmail(SendEmailRequest sendEmailRequest) {
        return commonEmailSender.sendEmail(sendEmailRequest);
    }

    public SendEmailResponse sendVerifyEmail(String emailAddress) {
        return commonEmailSender.sendVerifyEmail(emailAddress);
    }
}
