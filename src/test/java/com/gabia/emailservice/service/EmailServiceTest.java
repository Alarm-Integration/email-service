package com.gabia.emailservice.service;

import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import com.gabia.emailservice.sender.CommonEmailSender;
import org.assertj.core.util.Lists;
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

    @Test
    void 메일_발송_성공() {
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
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

    @Test
    void 인증_메일_발송_성공() {
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