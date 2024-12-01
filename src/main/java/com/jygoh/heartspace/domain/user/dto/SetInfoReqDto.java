package com.jygoh.heartspace.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SetInfoReqDto {

    private String encodeUserId;
    private String profileId;
    private String nickname;
    private String profileImgUrl;
    private String description;

    @Builder
    public SetInfoReqDto(String encodeUserId, String profileId, String nickname,
        String profileImgUrl, String description) {
        this.encodeUserId = encodeUserId;
        this.profileId = profileId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.description = description;
    }
}
