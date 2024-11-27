package com.jygoh.heartspace.global.security.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private String encodeUserId;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken, String encodeUserId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.encodeUserId = encodeUserId;
    }
}
