package com.jygoh.heartspace.domain.user.service;

import com.jygoh.heartspace.domain.user.dto.ProfileResDto;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.auth.dto.GoogleUserDto;
import com.jygoh.heartspace.global.security.jwt.dto.TokenResponseDto;
import com.jygoh.heartspace.global.security.jwt.service.JwtTokenProvider;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EncodeDecode encodeDecode;

    public UsersServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
        EncodeDecode encodeDecode) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encodeDecode = encodeDecode;
    }

    @Override
    public TokenResponseDto processingGoogleUser(GoogleUserDto userDto) {
        Optional<Users> optionalMember = userRepository.findByEmail(userDto.getEmail());
        TokenResponseDto tokenResponseDto = new TokenResponseDto();

        if (optionalMember.isPresent() && optionalMember.get().getNickname() != null) {
            String accessToken = jwtTokenProvider.createAccessToken(optionalMember.get().getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(optionalMember.get().getId());

            tokenResponseDto.setAccessToken(accessToken);
            tokenResponseDto.setRefreshToken(refreshToken);
        } else {
            Users newUser = Users.builder()
                .email(userDto.getEmail())
                .profileImgUrl(userDto.getProfileImgUrl())
                .provider("GOOGLE")
                .subjectId(userDto.getSubjectId())
                .build();
            tokenResponseDto.setEncodeUserId(encodeDecode.encode(newUser.getId()));
            userRepository.save(newUser);
        }

        return tokenResponseDto;
    }

    @Override
    public ProfileResDto getUserProfile(String profileId) {
        Users targetUser = userRepository.findByProfileId(profileId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        return ProfileResDto.builder()
            .profileId(targetUser.getProfileId())
            .profileImgUrl(targetUser.getProfileImgUrl())
            .nickname(targetUser.getNickname())
            .postCount(targetUser.getPostCount())
            .followerCount(targetUser.getFollowerCount())
            .followingCount(targetUser.getFollowingCount())
            .build();
    }

    @Override
    public void updateProfileImg(String profileImgUrl, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        user.updateProfileImgUrl(profileImgUrl);
    }

    @Override
    public boolean validateProfileId(String profileId) {
        return !userRepository.existsByProfileId(profileId);
    }
}
