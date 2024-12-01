package com.jygoh.heartspace.domain.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSessionManager {

    private final Map<Long, String> sessionIdToPrincipal = new ConcurrentHashMap<>();

    public void addSession(Long userId, String sessionId) {
        sessionIdToPrincipal.put(userId, sessionId);
    }

    public void removeSession(Long userId) {
        sessionIdToPrincipal.remove(userId);
    }

    public String getOtherPrincipal(Long userId) {
        return sessionIdToPrincipal.get(userId);
    }
}
