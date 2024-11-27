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
        Users user = userRepository.findById(encodeDecode.decode(reqDto.getEncodeUserId()))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        user.updateBeginningInfo(profileId, nickname);
        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setAccessToken(accessToken);
        tokenResponseDto.setRefreshToken(refreshToken);

        return tokenResponseDto;
    }
}
