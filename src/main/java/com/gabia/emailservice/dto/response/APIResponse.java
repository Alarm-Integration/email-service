package com.gabia.emailservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class APIResponse {
    private String message;
    private Object result;

    public static APIResponse withMessage(String message){
        return APIResponse.builder()
                .message(message)
                .build();
    }

    @Builder
    public APIResponse(String message) {
        this.message = message;
    }

    @Builder
    public APIResponse(String message, Object result) {
        this.message = message;
        this.result = result;
    }
}
