package com.jygoh.heartspace.global.security.auth.service;

import com.jygoh.heartspace.domain.user.dto.SetInfoReqDto;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;

public interface AuthService {

    TokenResponseDto login(String email, String password);

    TokenResponseDto setUserInfo(SetInfoReqDto reqDto);
}
