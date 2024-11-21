package com.jygoh.heartspace.domain.user.service;

import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UsersRepository;
import com.jygoh.heartspace.global.security.auth.dto.GoogleUserDto;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;
import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UsersServiceImpl(UsersRepository usersRepository, JwtTokenProvider jwtTokenProvider) {
        this.usersRepository = usersRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenResponseDto processingGoogleUser(GoogleUserDto userDto) {
        Optional<Users> optionalMember = usersRepository.findByEmail(userDto.getEmail());
        TokenResponseDto tokenResponseDto = new TokenResponseDto();

        if (optionalMember.isPresent()) {
            String accessToken = jwtTokenProvider.createAccessToken(optionalMember.get().getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(optionalMember.get().getId());

            tokenResponseDto.setAccessToken(accessToken);
            tokenResponseDto.setRefreshToken(refreshToken);
        } else {
            Users newUser = Users.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .profileImgUrl(userDto.getProfileImgUrl())
                .provider("GOOGLE")
                .subjectId(userDto.getSubjectId())
                .build();

            usersRepository.save(newUser);

            String accessToken = jwtTokenProvider.createAccessToken(newUser.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(newUser.getId());

            tokenResponseDto.setAccessToken(accessToken);
            tokenResponseDto.setRefreshToken(refreshToken);
        }

        return tokenResponseDto;
    }
}