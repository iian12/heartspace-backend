package com.jygoh.heartspace.domain.post.dto;

import com.jygoh.heartspace.domain.media.dto.MediaDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostReqDto {

    private String title;
    private String content;
    private List<MediaDto> mediaList;
    private String categoryName;
    private boolean isAnonymous;

    @Builder
    public CreatePostReqDto(String title, String content, List<MediaDto> mediaList,
        String categoryName, boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.mediaList = mediaList;
        this.categoryName = categoryName;
        this.isAnonymous = isAnonymous;
    }
}
