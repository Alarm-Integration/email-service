package com.gabia.emailservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendVerifyEmailRequest {
    private String emailAddress;
}
