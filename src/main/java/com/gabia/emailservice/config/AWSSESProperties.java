package com.gabia.emailservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws.ses.credentials")
public class AWSSESProperties {
    private final String accessKey;
    private final String secretKey;
}
