package com.jygoh.heartspace.domain.chat;

import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import jakarta.servlet.http.Cookie;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public WebSocketHandshakeInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = getJwtFromHeader(request.getHeaders());
        if (token == null) {
            token = getJwtFromCookies(request);
        }
        attributes.put("token", token);
        return jwtTokenProvider.validateToken(token);
    }

    private String getJwtFromHeader(HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // "Bearer " 부분 제거
        }
        return null;
    }

    private String getJwtFromCookies(ServerHttpRequest request) {
        // 쿠키에서 JWT를 찾기 위해 cookie 헤더를 파싱합니다.
        List<String> cookies = request.getHeaders().get("Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.contains("access_token=")) {
                    // "access_token="을 기준으로 토큰 추출
                    String token = cookie.split("access_token=")[1];
                    if (token.contains(";")) {
                        token = token.split(";")[0]; // 세미콜론 뒤에 불필요한 데이터가 있을 경우 제거
                    }
                    return token;
                }
            }
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Exception exception) {
    }
}
