package com.jygoh.heartspace.domain.hashtag.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String name;

    private int diaryCount;

    @Builder
    public DiaryHashtag(Long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.diaryCount = 0;
    }

    public void updateDiaryCount() {
        this.diaryCount++;
    }
}
