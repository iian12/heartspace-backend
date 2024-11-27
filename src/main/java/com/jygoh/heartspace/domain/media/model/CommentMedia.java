package com.jygoh.heartspace.domain.media.model;

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
public class CommentMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commentId;
    private String mediaUrl;
    private int orderIndex;

    @Builder
    public CommentMedia(Long commentId, String mediaUrl, int orderIndex) {
        this.commentId = commentId;
        this.mediaUrl = mediaUrl;
        this.orderIndex = orderIndex;
    }
}
