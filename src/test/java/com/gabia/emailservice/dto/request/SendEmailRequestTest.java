package com.gabia.emailservice.dto.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class SendEmailRequestTest {

    @Test
    void 동등성_비교_성공(){
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        assertThat(request1.equals(request2)).isTrue();
    }

    @Test
    void 동등성_비교_실패_발신자가_다른_경우(){
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("nameks171@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        assertThat(request1.equals(request2)).isFalse();
    }

    @Test
    void 동등성_비교_실패_수신자가_다른_경우(){
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks1@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        assertThat(request1.equals(request2)).isFalse();
    }

    @Test
    void 동등성_비교_실패_수신자가_다른_경우2(){
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks1@naver.com", "nameks2@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("nameks17@gmail.com")
                .raws(Lists.newArrayList("nameks1@naver.com", "nameks3@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        assertThat(request1.equals(request2)).isFalse();
    }
}