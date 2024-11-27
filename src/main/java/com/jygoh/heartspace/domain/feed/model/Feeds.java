package com.jygoh.heartspace.domain.feed.model;

import jakarta.persistence.Entity;
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
public class Feeds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;
    private boolean isUpdated;
    private LocalDateTime updatedAt;

    private int likeCount;
    private int commentCount;
    private int reportCount;

    @Builder
    public Feeds(Long userId, String content) {
        this.userId = userId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
        this.likeCount = 0;
        this.commentCount = 0;
        this.reportCount = 0;
    }

    public void update() {
        this.isUpdated = true;
        this.updatedAt = LocalDateTime.now();
    }
}
