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

    @Builder
    public SetInfoReqDto(String encodeUserId, String profileId, String nickname) {
        this.encodeUserId = encodeUserId;
        this.profileId = profileId;
        this.nickname = nickname;
    }
}
