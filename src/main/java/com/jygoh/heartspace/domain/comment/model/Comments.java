package com.jygoh.heartspace.domain.comment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private Long replyId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private CommentType commentType;
    private String content;
    private Long parentCommentId;

    private LocalDateTime createdAt;
    private boolean isUpdated;

    @Builder
    public Comments(Long postId, Long replyId, Long userId, CommentType commentType, String content, Long parentCommentId) {
        this.postId = postId;
        this.replyId = replyId;
        this.userId = userId;
        this.commentType = commentType;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
    }
}
