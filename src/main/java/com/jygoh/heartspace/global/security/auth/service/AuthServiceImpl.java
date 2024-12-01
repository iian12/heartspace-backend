package com.jygoh.heartspace.global.security.auth.service;

import com.jygoh.heartspace.domain.user.dto.SetInfoReqDto;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;
import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EncodeDecode encodeDecode;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, EncodeDecode encodeDecode,
        JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.encodeDecode = encodeDecode;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenResponseDto login(String email, String password) {
        return null;
    }

    @Override
    public TokenResponseDto setUserInfo(SetInfoReqDto reqDto) {
        String profileId = reqDto.getProfileId();
        String nickname = reqDto.getNickname();
        String profileImgUrl = reqDto.getProfileImgUrl();
        String description = reqDto.getDescription();
        Users user = userRepository.findById(encodeDecode.decode(reqDto.getEncodeUserId()))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        if (!profileId.matches("^[a-zA-Z0-9_.]+$")) {
            throw new IllegalArgumentException("영문자, 숫자, _, .만 사용 가능합니다.");
        }

        if (userRepository.existsByProfileId(profileId)) {
            throw new IllegalArgumentException("이미 사용 중인 프로필 ID 입니다.");
        }

        user.updateBeginningInfo(profileId, nickname, profileImgUrl, description);
        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setAccessToken(accessToken);
        tokenResponseDto.setRefreshToken(refreshToken);

        return tokenResponseDto;
    }
}
