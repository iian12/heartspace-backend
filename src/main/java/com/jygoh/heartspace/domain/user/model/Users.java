package com.jygoh.heartspace.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String profileId;

    private String nickname;

    private String profileImgUrl;
    private String description;

    private String provider;

    private String subjectId;

    private int postCount;

    private int followerCount;
    private int followingCount;

    private boolean isSignUp;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Users(String email, String password, String profileId, String nickname,
        String profileImgUrl, String provider, String subjectId) {
        this.email = email;
        this.password = password;
        this.profileId = profileId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.provider = provider;
        this.subjectId = subjectId;
        this.postCount = 0;
        this.followerCount = 0;
        this.followingCount = 0;
        this.isSignUp = false;
        this.role = Role.USER;
    }

    public void updateBeginningInfo(String profileId, String nickname, String profileImgUrl,
        String description) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.description = description;
        this.isSignUp = true;
    }

    public void updateProfileId(String profileId) {
        this.profileId = profileId;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void updateSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void signUpComplete() {
        this.isSignUp = true;
    }
}
