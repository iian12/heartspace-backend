package com.jygoh.heartspace.domain.media.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MediaDto {

    private String mediaUrl;
    private Integer orderIndex;

    @Builder
    public MediaDto(String mediaUrl, Integer orderIndex) {
        this.mediaUrl = mediaUrl;
        this.orderIndex = orderIndex;
    }
}
