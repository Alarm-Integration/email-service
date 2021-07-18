package com.gabia.emailservice.sender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.util.MemoryAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AWSEmailSenderTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @InjectMocks
    private AWSEmailSender amazonEmailSender;

    private MemoryAppender memoryAppender;

    private String sender = "nameks17@gmail.com";
    private List<String> addresses = Arrays.asList("nameks@naver.com");
    private String title = "제목";
    private String content = "내용";
    private String traceId = "abc";
    private Long userId = 1L;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(AWSEmailSender.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    void 메일_발송_성공() {
        //given
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .sender(sender)
                .addresses(addresses)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        SendEmailResult sendEmailResult = new SendEmailResult().withMessageId("123");
        given(amazonSimpleEmailService.sendEmail(sendEmailRequest.toAWSRequest())).willReturn(sendEmailResult);

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendEmail(sendEmailRequest));

        //then
        assertThat(throwable).isNull();
    }

    @Test
    void 메일_발송_실패_인증하지_않은_발신자() {
        //given
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .sender("notverified@email.com")
                .addresses(addresses)
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        MessageRejectedException exception = new MessageRejectedException("exception");
        exception.setErrorCode("MessageRejected");

        given(amazonSimpleEmailService.sendEmail(sendEmailRequest.toAWSRequest()))
                .willThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendEmail(sendEmailRequest));

        //then
        assertThat(throwable).isInstanceOf(Exception.class).hasMessageContaining("MessageRejected");
    }

    @Test
    void 메일_발송_실패_addresses_이메일_형식_아님() {
        //given
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .sender(sender)
                .addresses(Arrays.asList("nameks@naver.com", "01012345678"))
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        AmazonSimpleEmailServiceException exception = new AmazonSimpleEmailServiceException("exception");
        exception.setErrorCode("InvalidParameterValue");

        given(amazonSimpleEmailService.sendEmail(sendEmailRequest.toAWSRequest()))
                .willThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendEmail(sendEmailRequest));

        //then
        assertThat(throwable).isInstanceOf(Exception.class).hasMessageContaining("InvalidParameterValue");
    }

    @Test
    void 메일_발송_실패_비어있는_addresses() {
        //given
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .sender(sender)
                .addresses(Arrays.asList())
                .title(title)
                .content(content)
                .userId(userId)
                .traceId(traceId)
                .build();

        AmazonSimpleEmailServiceException exception = new AmazonSimpleEmailServiceException("exception");
        exception.setErrorCode("ValidationError");

        given(amazonSimpleEmailService.sendEmail(sendEmailRequest.toAWSRequest()))
                .willThrow(exception);

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendEmail(sendEmailRequest));

        //then
        assertThat(throwable).isInstanceOf(Exception.class).hasMessageContaining("ValidationError");
    }

    @Test
    void 인증_메일_발송_성공() {
        //given
        String emailAddress = "nameks@naver.com";

        VerifyEmailIdentityResult verifyEmailIdentityResult = mock(VerifyEmailIdentityResult.class);

        given(amazonSimpleEmailService.verifyEmailIdentity(new VerifyEmailIdentityRequest().withEmailAddress(emailAddress)))
                .willReturn(verifyEmailIdentityResult);

        //when
        amazonEmailSender.sendVerifyEmail(emailAddress);

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendVerifyEmail(emailAddress));

        //then
        assertThat(throwable).isNull();
    }
}