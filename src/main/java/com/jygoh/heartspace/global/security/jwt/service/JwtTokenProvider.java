package com.jygoh.heartspace.global.security.jwt.service;

import com.jygoh.heartspace.global.security.utils.EncryptionUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-ms}")
    private long accessTokenValidityInMs;

    @Value("${jwt.refresh-token-validity-in-ms}")
    private long refreshTokenValidityInMilliseconds;

    private Key key;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long userId) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }
            Map<String, Object> claims = new HashMap<>();

            String encryptedUserId = EncryptionUtils.encrypt(userId.toString());

            claims.put("userId", encryptedUserId);
            Date now = new Date();
            Date validity = new Date(now.getTime() + accessTokenValidityInMs);
            return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256).compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create access token", e);
        }
    }

    public String createRefreshToken(Long userId) {
        try {
            String encryptedMemberId = EncryptionUtils.encrypt(userId.toString());
            Date now = new Date();
            Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
            return Jwts.builder().setSubject(encryptedMemberId).setIssuedAt(now)
                .setExpiration(validity).signWith(key, SignatureAlgorithm.HS256).compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create refresh token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }

            String bearerToken = token.trim();

            if (!bearerToken.trim().isEmpty() && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
            }

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
