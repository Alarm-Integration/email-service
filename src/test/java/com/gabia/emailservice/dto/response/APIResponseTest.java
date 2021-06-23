package com.gabia.emailservice.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class APIResponseTest {

    @Test
    void withMessage_인스턴스_생성() {
        //given
        String message = "hello";

        //when
        APIResponse apiResponse = APIResponse.withMessage(message);

        //then
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getResult()).isNull();
    }
}