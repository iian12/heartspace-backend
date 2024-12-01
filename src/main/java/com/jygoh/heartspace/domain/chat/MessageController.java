package com.jygoh.heartspace.domain.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final ChatService chatService;

    public MessageController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/send/{profileId}")
    public void sendMessage(@DestinationVariable String profileId, SimpMessageHeaderAccessor sha,
        @Payload String content) {

        String token = (String) sha.getSessionAttributes().get("token");

        chatService.sendMessage(content, profileId, token);
    }
}
