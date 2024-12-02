package com.jygoh.heartspace.domain.chat;

public interface ChatService {
    void sendMessage(String content, String profileId, Long userId);
}
