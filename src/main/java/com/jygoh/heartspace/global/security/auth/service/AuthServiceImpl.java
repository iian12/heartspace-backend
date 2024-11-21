package com.jygoh.heartspace.global.security.auth.service;

import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public TokenResponseDto login(String email, String password) {
        return null;
    }
}
