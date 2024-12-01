package com.jygoh.heartspace.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageDto {

    private String senderProfileImgUrl;
    private String senderProfileId;
    private String senderNickname;
    private String content;
    private String timestamp;
    private String roomId;
    @Builder
    public ChatMessageDto(String senderProfileImgUrl, String senderProfileId, String senderNickname, String content, String timestamp,
        String roomId) {
        this.senderProfileImgUrl = senderProfileImgUrl;
        this.senderProfileId = senderProfileId;
        this.senderNickname = senderNickname;
        this.content = content;
        this.timestamp = timestamp;
        this.roomId = roomId;
    }
}
