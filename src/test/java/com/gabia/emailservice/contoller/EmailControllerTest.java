package com.gabia.emailservice.contoller;

import com.gabia.emailservice.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("/email-service%s", path));
    }

    @Test
    void 인증_메일_발송_성공() throws Exception {
        //given
        String emailAddress = "nameks@naver.com";

        doNothing().when(emailService).sendVerifyEmail(emailAddress);

        //when
        ResultActions result = mockMvc.perform(post(uri("/verify-email/" + emailAddress))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("인증 메일 발송 완료"));

    }
}