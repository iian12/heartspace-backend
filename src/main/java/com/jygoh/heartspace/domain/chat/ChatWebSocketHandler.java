package com.jygoh.heartspace.domain.chat;

import com.jygoh.heartspace.global.security.utils.TokenUtils;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;

    public ChatWebSocketHandler(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = (String) session.getAttributes().get("token");
        Long userId = TokenUtils.getUserIdFromToken(token);

        session.getAttributes().put("userId", userId);
        sessionManager.addSession(userId, Objects.requireNonNull(session.getPrincipal()).getName());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
        throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        sessionManager.removeSession(userId);
    }
}
