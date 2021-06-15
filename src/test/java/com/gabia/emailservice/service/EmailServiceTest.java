package com.gabia.emailservice.service;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import com.gabia.emailservice.sender.CommonEmailSender;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private CommonEmailSender commonEmailSender;

    @InjectMocks
    private EmailService emailService;

    @DisplayName("email 전송 테스트")
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

        given(commonEmailSender.sendEmail(request)).willReturn(response);

        //when
        SendEmailResponse result = emailService.sendEmail(request);

        //then
        assertThat(result.getMessage()).isEqualTo(response.getMessage());
    }

    @DisplayName("인증 이메일 전송 테스트")
    @Test
    void sendVerifyEmail() {
        //given
        String emailAddress = "nameks@naver.com";

        SendEmailResponse response = SendEmailResponse.builder()
                .message("ok")
                .build();

        given(commonEmailSender.sendVerifyEmail(emailAddress)).willReturn(response);

        //when
        SendEmailResponse result = emailService.sendVerifyEmail(emailAddress);

        //then
        assertThat(result.getMessage()).isEqualTo(response.getMessage());
    }

}