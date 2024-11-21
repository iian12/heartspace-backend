package com.jygoh.heartspace.global.security.auth.service;

import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UsersRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;

    public CustomOAuth2UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = getAttribute(oAuth2User, provider, "email");
        String profileImageUrl = getAttribute(oAuth2User, provider, "picture");
        String subjectId = getAttribute(oAuth2User, provider, "sub");
        String name = getAttribute(oAuth2User, provider, "name");
        Users user = usersRepository.findByEmail(email).map(existingMember -> {
            if (!existingMember.getSubjectId().equals(subjectId)) {
                existingMember.updateSubjectId(subjectId);
                usersRepository.save(existingMember);
            }
            return existingMember;
        }).orElseGet(() -> {
            Users newUser = Users.builder().email(email).nickname(name)
                .profileImgUrl(profileImageUrl).provider("GOOGLE").subjectId(subjectId).build();
            return usersRepository.save(newUser);
        });
        return new CustomUserDetail(user, user.getId());
    }

    private String getAttribute(OAuth2User oAuth2User, String provider, String attributeName) {
        return switch (provider) {
            case "google" -> (String) oAuth2User.getAttribute(attributeName);
            default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }
}
