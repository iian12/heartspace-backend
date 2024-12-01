package com.jygoh.heartspace.domain.post.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;
    private String content;
    private String thumbnailUrl;

    @ElementCollection
    private List<Long> hashtagIds;

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDateTime createdAt;
    private boolean isUpdated;
    private LocalDateTime updatedAt;

    private int viewCount;
    private int likeCount;
    private int commentCount;
    private int answerCount;
    private int reportCount;

    @Builder
    public Posts(Long userId, String title, String thumbnailUrl, String content, List<Long> hashtagIds, Type type) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.hashtagIds = hashtagIds;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
        this.updatedAt = null;
        this.viewCount = 0;
        this.likeCount = 0;
        this.commentCount = 0;
        this.answerCount = 0;
        this.reportCount = 0;
    }

    public void updatePosts(String newTitle, String newContent, List<Long> newHashtagIds) {
        this.title = newTitle;
        this.content = newContent;
        this.hashtagIds = newHashtagIds;
        this.isUpdated = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateThumbnailUrl(String newThumbnailUrl) {
        this.thumbnailUrl = newThumbnailUrl;
    }
}
