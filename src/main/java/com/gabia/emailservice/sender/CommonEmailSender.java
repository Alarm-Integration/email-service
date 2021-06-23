package com.gabia.emailservice.sender;

import com.gabia.emailservice.dto.request.SendEmailRequest;

public interface CommonEmailSender {
    void sendEmail(SendEmailRequest sendEmailRequest) throws Exception;

    default void sendVerifyEmail(String emailAddress) {

    }
}
