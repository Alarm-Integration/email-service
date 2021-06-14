package com.gabia.emailservice.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AmazonEmailServiceTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @InjectMocks
    private AmazonEmailService amazonEmailService;

    @DisplayName("AWS SES email 전송 테스트")
    @Test
    void send_email(){
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .from("nameks17@gmail.com")
                .to(Lists.newArrayList("nameks@naver.com"))
                .subject("테스트")
                .content("안녕하세요")
                .build();

        SendEmailResponse response = SendEmailResponse.builder()
                .message("123")
                .build();

        given(amazonSimpleEmailService.sendEmail(request.toSendRequestDto())).willReturn(new SendEmailResult().withMessageId("123"));

        //when
        SendEmailResponse result = amazonEmailService.send(request);

        //then
        assertThat(result.getMessage()).isEqualTo(response.getMessage());
    }

    @DisplayName("AWS SES 인증 이메일 전송 테스트")
    @Test
    void sendVerifyEmail() {
        //given
        String emailAddress = "nameks@naver.com";

        VerifyEmailIdentityResult verifyEmailIdentityResult = mock(VerifyEmailIdentityResult.class);
        
        given(amazonSimpleEmailService.verifyEmailIdentity(new VerifyEmailIdentityRequest().withEmailAddress(emailAddress)))
                .willReturn(verifyEmailIdentityResult);

        SendEmailResponse response = SendEmailResponse.builder()
                .message("ok")
                .build();

        //when
        SendEmailResponse result = amazonEmailService.sendVerifyEmail(emailAddress);

        //then
        assertThat(result.getMessage()).isEqualTo(response.getMessage());
    }
}