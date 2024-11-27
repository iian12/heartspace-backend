package com.jygoh.heartspace.domain.media.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PostMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private String mediaUrl;
    private int orderIndex;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Builder
    public PostMedia(Long postId, String mediaUrl, int orderIndex, MediaType mediaType) {
        this.postId = postId;
        this.mediaUrl = mediaUrl;
        this.orderIndex = orderIndex;
        this.mediaType = mediaType;
    }
}
