package com.jygoh.heartspace.domain.comment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long postId;
    private Long parentId;

    private String content;

    private LocalDateTime createdAt;
    private boolean isUpdated;

    private int likeCount;
    private int reportCount;

    public PostComments(Long userId, Long postId, Long parentId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.parentId = (parentId == null) ? 0L : parentId;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
        this.likeCount = 0;
        this.reportCount = 0;
    }

    public void updateComment(String newContent) {
        this.content = newContent;
        this.isUpdated = true;
    }
}
