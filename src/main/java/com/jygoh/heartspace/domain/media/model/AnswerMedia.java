package com.jygoh.heartspace.domain.media.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AnswerMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long answerId;
    private String mediaUrl;
    private int orderIndex;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Builder
    public AnswerMedia(Long answerId, String mediaUrl, int orderIndex, MediaType mediaType) {
        this.answerId = answerId;
        this.mediaUrl = mediaUrl;
        this.orderIndex = orderIndex;
        this.mediaType = mediaType;
    }
}
