package com.jygoh.heartspace.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostDto {

    private String title;
    private String content;
    private boolean isAnonymous;
}
