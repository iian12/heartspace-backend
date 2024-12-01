package com.jygoh.heartspace.domain.user.service;

import com.jygoh.heartspace.domain.user.dto.ProfileResDto;
import com.jygoh.heartspace.global.security.auth.dto.GoogleUserDto;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;

public interface UsersService {

    TokenResponseDto processingGoogleUser(GoogleUserDto userDto);

    ProfileResDto getUserProfile(String profileId);

    void updateProfileImg(String profileImgUrl, String token);

    boolean validateProfileId(String profileId);
}
