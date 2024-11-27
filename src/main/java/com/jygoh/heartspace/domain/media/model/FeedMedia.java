package com.jygoh.heartspace.domain.media.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long feedId;
    private String mediaUrl;
    private int orderIndex;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    public FeedMedia(Long feedId, String mediaUrl, int orderIndex, MediaType mediaType) {
        this.feedId = feedId;
        this.mediaUrl = mediaUrl;
        this.orderIndex = orderIndex;
        this.mediaType = mediaType;
    }
}
