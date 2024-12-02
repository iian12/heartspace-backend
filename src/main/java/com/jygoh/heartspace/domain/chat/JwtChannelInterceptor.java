package com.jygoh.heartspace.domain.chat;

import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final WebSocketSessionManager sessionManager;

    private final JwtTokenProvider jwtTokenProvider;

    public JwtChannelInterceptor(JwtTokenProvider jwtTokenProvider,
        WebSocketSessionManager sessionManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.sessionManager = sessionManager;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            log.info("JWT Token: " + jwtToken);
            log.info("Command: " + accessor.getCommand() + ", SessionID: " + accessor.getSessionId());

            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                if (!jwtTokenProvider.validateToken(jwtToken)) {
                    throw new IllegalArgumentException("Invalid JWT token");
                }
                jwtToken = jwtToken.substring(7);
                Long userId = TokenUtils.getUserIdFromToken(jwtToken);
                String uuid = UUID.randomUUID().toString();
                sessionManager.addSession(userId, uuid);
                Objects.requireNonNull(accessor.getSessionAttributes()).put("userId", userId);

                StompPrincipal stompPrincipal = new StompPrincipal(uuid);
                accessor.setUser(stompPrincipal);
            }
        }
        return message;
    }

}
