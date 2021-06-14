package com.gabia.emailservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SendEmailResponse {
    private String message;

    @Builder
    public SendEmailResponse(String message) {
        this.message = message;
    }
}