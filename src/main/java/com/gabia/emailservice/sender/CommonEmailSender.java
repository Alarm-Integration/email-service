package com.gabia.emailservice.sender;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;

public interface CommonEmailSender {
    SendEmailResponse sendEmail(SendEmailRequest sendEmailRequest);

    default SendEmailResponse sendVerifyEmail(String emailAddress) {
        return null;
    }
}
