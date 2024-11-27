package com.jygoh.heartspace.global.handler;

import com.jygoh.heartspace.global.security.auth.service.CustomUserDetail;
import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final EncodeDecode encodeDecode;

    public CustomOAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider, EncodeDecode encodeDecode) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.encodeDecode = encodeDecode;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetail.getUserId();

        if (userDetail.getNickname() != null) {
            String accessToken = jwtTokenProvider.createAccessToken(userId);
            String refreshToken = jwtTokenProvider.createRefreshToken(userId);
            Cookie accessTokenCookie = createCookie("access_token", accessToken);
            Cookie refreshTokenCookie = createCookie("refresh_token", refreshToken);
            response.setStatus(HttpServletResponse.SC_OK);
            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);
        } else {
            String encodedUserId = encodeDecode.encode(userId);
            response.setStatus(HttpStatus.PRECONDITION_REQUIRED.value());
            response.setHeader("X-User-ID", encodedUserId);
        }
    }

    private Cookie createCookie(String name, String token) {
        if (name.equals("accessToken")) {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 2);
            return cookie;
        } else {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 14);
            return cookie;
        }
    }
}

