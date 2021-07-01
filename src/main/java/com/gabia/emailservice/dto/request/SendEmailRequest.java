package com.gabia.emailservice.dto.request;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import lombok.*;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class SendEmailRequest {
    private String sender;
    private List<String> raws;
    private String title;
    private String content;
    private String traceId;
    private Long userId;

    @Builder
    public SendEmailRequest(String sender, List<String> raws, String title, String content, String traceId, Long userId) {
        this.sender = sender;
        this.raws = raws;
        this.title = title;
        this.content = content;
        this.traceId = traceId;
        this.userId = userId;
    }

    public com.amazonaws.services.simpleemail.model.SendEmailRequest toAWSRequest() {
        Destination destination = new Destination()
                .withToAddresses(this.raws);

        Message message = new Message()
                .withSubject(createContent(this.title))
                .withBody(new Body().withHtml(createContent(this.content)));

        return new com.amazonaws.services.simpleemail.model.SendEmailRequest()
                .withSource(this.sender)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(String text) {
        return new Content()
                .withCharset("UTF-8")
                .withData(text);
    }
}