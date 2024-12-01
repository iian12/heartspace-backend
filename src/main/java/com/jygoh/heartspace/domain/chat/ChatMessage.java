package com.jygoh.heartspace.domain.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long roomId;

    private String content;
    private LocalDateTime timeStamp;

    @Builder
    public ChatMessage(Long senderId, Long roomId, String content) {
        this.senderId = senderId;
        this.roomId = roomId;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }
}
