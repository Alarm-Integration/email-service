package com.gabia.emailservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Setter
@Getter
@ConfigurationProperties(prefix = "aws.ses")
public class AWSSESProperties {

    private String region;
    private Credentials credentials = new Credentials();

    @Setter
    @Getter
    public class Credentials{
        private String accessKey;
        private String secretKey;
    }
}
