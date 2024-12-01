package com.jygoh.heartspace.domain.diary.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;
    private String content;
    private LocalDate date;
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private DiaryEmotion emotion;

    @ElementCollection
    private List<Long> hashtagIds;

    private LocalDateTime createdAt;

    @Builder
    public Diary(Long userId, String title, String content, String thumbnailUrl, LocalDate date, DiaryEmotion emotion,
        List<Long> hashtagIds) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.date = date;
        this.emotion = emotion;
        this.hashtagIds = hashtagIds != null ? hashtagIds : new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public void updateDiary(String newTitle, String newContent, List<Long> newHashtagIds) {
        this.title = newTitle;
        this.content = newContent;
        this.hashtagIds = newHashtagIds;
    }

    public void updateThumbnailUrl(String newThumbnailUrl) {
        this.thumbnailUrl = newThumbnailUrl;
    }
}
