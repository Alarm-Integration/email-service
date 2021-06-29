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

    @Test
    void 해시코드_비교_성공() {
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

        //when
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        //then
        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    void 해시코드_비교_실패_sender_다른_경우() {
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("1")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("2")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("테스트")
                .content("안녕하세요")
                .build();

        //when
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        //then
        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

    @Test
    void 해시코드_비교_실패_title_다른_경우() {
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("sender@email.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("제목1")
                .content("내용")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("sender@email.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("제목2")
                .content("내용")
                .build();

        //when
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        //then
        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

    @Test
    void 해시코드_비교_실패_content_다른_경우() {
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("sender@email.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("제목1")
                .content("내용")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("sender@email.com")
                .raws(Lists.newArrayList("nameks@naver.com"))
                .title("제목1")
                .content("내용2")
                .build();

        //when
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        //then
        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

    @Test
    void 해시코드_비교_실패_raws_다른_경우() {
        //given
        SendEmailRequest request1 = SendEmailRequest.builder()
                .sender("sender@email.com")
                .raws(Lists.newArrayList("nameks1@naver.com"))
                .title("제목1")
                .content("내용1")
                .build();

        SendEmailRequest request2 = SendEmailRequest.builder()
                .sender("sender@email.com")
                .raws(Lists.newArrayList("nameks2@naver.com"))
                .title("제목1")
                .content("내용1")
                .build();

        //when
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        //then
        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

}