package com.jygoh.heartspace.domain.hashtag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostHashtagDto {

    private String hashtagId;
    private String hashtagName;

    @Builder
    public PostHashtagDto(String hashtagId, String hashtagName) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }
}
