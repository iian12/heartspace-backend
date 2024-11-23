package com.jygoh.heartspace.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostReqDto {

    private String title;
    private String content;
    private String categoryName;
    private boolean isAnonymous;

    @Builder
    public CreatePostReqDto(String title, String content, String categoryName,
        boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.isAnonymous = isAnonymous;
    }
}
