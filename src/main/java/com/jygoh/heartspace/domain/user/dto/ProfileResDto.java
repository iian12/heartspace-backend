package com.jygoh.heartspace.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResDto {

    private String profileId;
    private String nickname;
    private String profileImgUrl;
    private int postCount;
    private int followerCount;
    private int followingCount;

    @Builder
    public ProfileResDto(String profileId, String nickname, String profileImgUrl, int postCount,
        int followerCount, int followingCount) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;

    }
}
