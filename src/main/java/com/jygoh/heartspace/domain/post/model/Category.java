package com.jygoh.heartspace.domain.post.model;

import java.util.Arrays;
import java.util.Optional;

public enum Category {
    FREE,               // 자유
    HUMAN_RELATIONS,    // 인간관계
    CAREER,             // 진로, 직장
    LOVE,               // 사랑, 연애
    SELF_DEVELOPMENT,   // 자기계발
    MENTAL_HEALTH,      // 정신 건강
    FAMILY,             // 가족
    LIFE_PURPOSE,       // 인생
    SOCIAL_ISSUES,      // 사회
    ETC;                // 기타

    public static Optional<Category> fromString(String value) {
        return Arrays.stream(Category.values())
            .filter(category -> category.name().equalsIgnoreCase(value))
            .findFirst();
    }
}
