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
public class FeedComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long feedId;

    private Long content;

    private LocalDateTime createdAt;
    private boolean isUpdated;

    private int likeCount;
    private int reportCount;

    public FeedComments(Long userId, Long feedId, Long content) {
        this.userId = userId;
        this.feedId = feedId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
        this.likeCount = 0;
        this.reportCount = 0;
    }
}
