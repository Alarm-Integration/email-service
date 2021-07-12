package com.gabia.emailservice.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AlarmMessage {
    private Long userId;
    private String traceId;
    private Long groupId;
    private List<String> receivers;
    private String title;
    private String content;

    @Builder
    public AlarmMessage(Long userId, String traceId, Long groupId, List<String> receivers, String title, String content) {
        this.userId = userId;
        this.traceId = traceId;
        this.groupId = groupId;
        this.receivers = receivers;
        this.title = title;
        this.content = content;
    }
}