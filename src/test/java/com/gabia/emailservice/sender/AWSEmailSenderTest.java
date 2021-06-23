package com.gabia.emailservice.sender;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AWSEmailSenderTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @InjectMocks
    private AWSEmailSender awsEmailSender;

    @Test
    void 메일_발송_성공(){
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailResponse response = SendEmailResponse.builder()
                .message("메일 발송 완료")
                .build();

        given(amazonSimpleEmailService.sendEmail(request.toSendRequestDto()))
                .willReturn(new SendEmailResult().withMessageId(response.getMessage()));

        //when
        SendEmailResponse result = awsEmailSender.sendEmail(request);

        //then
        assertThat(result.getMessage()).isEqualTo(response.getMessage());
    }

    @Test
    void 메일_발송_실패_인증하지_않은_발신자(){
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .sender("notverified@email.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        given(amazonSimpleEmailService.sendEmail(request.toSendRequestDto()))
                .willThrow(new MessageRejectedException("Email address is not verified"));

        //when
        SendEmailResponse sendEmailResponse = awsEmailSender.sendEmail(request);

        //then
        assertThat(sendEmailResponse.getMessage()).isEqualTo("Email address is not verified");
    }

    @Test
    void 인증_메일_발송_성공() {
        //given
        String emailAddress = "nameks@naver.com";

        VerifyEmailIdentityResult verifyEmailIdentityResult = mock(VerifyEmailIdentityResult.class);

        given(amazonSimpleEmailService.verifyEmailIdentity(new VerifyEmailIdentityRequest().withEmailAddress(emailAddress)))
                .willReturn(verifyEmailIdentityResult);

        //when
        SendEmailResponse result = awsEmailSender.sendVerifyEmail(emailAddress);

        //then
        assertThat(result.getMessage()).isEqualTo("인증 메일 발송 완료");
    }
}