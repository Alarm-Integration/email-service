package com.gabia.emailservice.sender;

import com.gabia.emailservice.dto.request.SendEmailRequest;

public interface CommonEmailSender {
    boolean sendEmail(SendEmailRequest sendEmailRequest);

    default void sendVerifyEmail(String emailAddress) {

    }
}
