package com.gabia.emailservice.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.emailservice.dto.request.SendEmailRequest;
import com.gabia.emailservice.dto.response.SendEmailResponse;
import com.gabia.emailservice.service.EmailService;
import org.assertj.core.util.Lists;
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

import static org.mockito.BDDMockito.given;
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

    @Test
    void 메일_발송_성공() throws Exception {
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

        given(emailService.sendEmail(request)).willReturn(response);

        //when
        ResultActions result = mockMvc.perform(post("/email")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("메일 발송 완료"));

    }

    @Test
    void 인증_메일_발송_성공() throws Exception {
        //given
        String emailAddress = "nameks@naver.com";
        String url = "/verify-email/" + emailAddress;

        given(emailService.sendVerifyEmail(emailAddress))
                .willReturn(SendEmailResponse.withMessage("인증 메일 발송 완료"));

        //when
        ResultActions result = mockMvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("인증 메일 발송 완료"));

    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}