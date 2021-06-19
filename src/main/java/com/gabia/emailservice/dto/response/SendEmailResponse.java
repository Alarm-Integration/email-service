package com.gabia.emailservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendEmailResponse {
    private String message;

    @Builder
    public SendEmailResponse(String message) {
        this.message = message;
    }

    public static SendEmailResponse withMessage(String message){
        return SendEmailResponse.builder()
                .message(message)
                .build();
    }
}