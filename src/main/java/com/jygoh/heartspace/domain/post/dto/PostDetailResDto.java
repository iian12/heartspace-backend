package com.jygoh.heartspace.domain.post.dto;

import com.jygoh.heartspace.domain.hashtag.dto.PostHashtagDto;
import com.jygoh.heartspace.domain.media.dto.MediaDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDetailResDto {

    private String postId;
    private String title;
    private String content;
    private String authorProfileImgUrl;
    private String authorProfileId;
    private String authorNickname;
    private List<PostHashtagDto> hashtags;
    private String type;
    private List<MediaDto> mediaList;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private LocalDateTime createdAt;
    private boolean isUpdated;
    private LocalDateTime updatedAt;

    @Builder
    public PostDetailResDto(String postId, String title, String content, String authorProfileImgUrl, String authorProfileId, String authorNickname,
        List<PostHashtagDto> hashtags, String type, List<MediaDto> mediaList, int commentCount,
        int viewCount, int likeCount, LocalDateTime createdAt, boolean isUpdated, LocalDateTime updatedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.authorProfileImgUrl = authorProfileImgUrl;
        this.authorProfileId = authorProfileId;
        this.authorNickname = authorNickname;
        this.hashtags = hashtags;
        this.type = type;
        this.mediaList = mediaList;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isUpdated = isUpdated;
    }
}
