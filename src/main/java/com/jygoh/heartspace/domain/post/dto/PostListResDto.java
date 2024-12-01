package com.jygoh.heartspace.domain.post.dto;

import com.jygoh.heartspace.domain.hashtag.dto.PostHashtagDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListResDto {

    private String postId;
    private String title;
    private String thumbnailUrl;
    private String authorProfileImgUrl;
    private String authorProfileId;
    private String authorNickname;
    private boolean canViewAuthor;
    private List<PostHashtagDto> hashtags;
    private boolean mediaExists;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private LocalDateTime createdAt;

    @Builder
    public PostListResDto(String postId, String title, String thumbnailUrl, String authorProfileImgUrl, String authorProfileId, String authorNickname, boolean canViewAuthor, List<PostHashtagDto> hashtags,
        boolean mediaExists, int commentCount, int viewCount, int likeCount, LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.authorProfileImgUrl = authorProfileImgUrl;
        this.authorProfileId = authorProfileId;
        this.authorNickname = authorNickname;
        this.canViewAuthor = canViewAuthor;
        this.hashtags = hashtags;
        this.mediaExists = mediaExists;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }
}
