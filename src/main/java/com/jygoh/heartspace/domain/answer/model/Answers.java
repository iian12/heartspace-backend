package com.jygoh.heartspace.domain.answer.model;

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
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long postId;

    private String content;

    private LocalDateTime createdAt;
    private boolean isUpdated;
    private LocalDateTime updatedAt;

    private int likeCount;
    private int reportCount;

    @Builder
    public Answers(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
        this.likeCount = 0;
        this.reportCount = 0;
    }

    public void update() {
        this.isUpdated = true;
        this.updatedAt = LocalDateTime.now();
    }
}
