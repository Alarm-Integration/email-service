package com.gabia.emailservice.sender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.util.MemoryAppender;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AWSEmailSenderTest {

    @Mock
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @InjectMocks
    private AWSEmailSender amazonEmailSender;

    private MemoryAppender memoryAppender;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    void 메일_발송_성공() throws Exception {
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailResult sendEmailResult = mock(SendEmailResult.class);
        given(amazonSimpleEmailService.sendEmail(request.toAWSRequest())).willReturn(sendEmailResult);

        //when
        amazonEmailSender.sendEmail(request);

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(0);
    }

    @Test
    void 메일_발송_실패_인증하지_않은_발신자() {
        //given
        SendEmailRequest request = SendEmailRequest.builder()
                .sender("notverified@email.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        given(amazonSimpleEmailService.sendEmail(request.toAWSRequest()))
                .willThrow(new MessageRejectedException("Email address is not verified"));

        //when
        Throwable throwable = catchThrowable(() -> amazonEmailSender.sendEmail(request));

        //then
        assertThat(throwable).isInstanceOf(Exception.class)
                .hasMessageContaining("인증된 발신자가 아닙니다.");
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

        //then
        assertThat(memoryAppender.getSize()).isEqualTo(0);
    }
}