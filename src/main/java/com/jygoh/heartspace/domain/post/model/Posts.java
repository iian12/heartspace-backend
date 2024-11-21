package com.jygoh.heartspace.domain.post.model;

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
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean isAnonymous;

    private LocalDateTime createdAt;

    private boolean isUpdated;

    private LocalDateTime updatedAt;

    @Builder
    public Posts(Long userId, String title, String content, Category category, boolean isAnonymous) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.isAnonymous = isAnonymous;
        this.createdAt = LocalDateTime.now();
        this.isUpdated = false;
        this.updatedAt = null;
    }

    public void updatePosts(String newTitle, String newContent, boolean isAnonymous) {
        this.title = newTitle;
        this.content = newContent;
        this.isAnonymous = isAnonymous;
        this.isUpdated = true;
        this.updatedAt = LocalDateTime.now();
    }
}
