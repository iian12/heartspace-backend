package com.jygoh.heartspace.global.security.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class GoogleUserDto {

    private String email;
    private String profileImgUrl;
    private String subjectId;

    @Builder
    public GoogleUserDto(String email, String profileImgUrl, String subjectId) {
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.subjectId = subjectId;
    }
}
