package com.jygoh.heartspace.domain.user.service;

import com.jygoh.heartspace.global.security.auth.dto.GoogleUserDto;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;

public interface UsersService {

    TokenResponseDto processingGoogleUser(GoogleUserDto userDto);
}
