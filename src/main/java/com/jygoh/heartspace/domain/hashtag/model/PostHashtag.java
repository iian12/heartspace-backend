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
public class PostHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int postCount;

    @Builder
    public PostHashtag(String name) {
        this.name = name;
        this.postCount = 0;
    }

    public void updatePostCount() {
        this.postCount++;
    }
}
