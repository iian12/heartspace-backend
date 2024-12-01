package com.jygoh.heartspace.domain.diary.model;

public enum DiaryEmotion {

    HAPPY,
    EXCITED,
    CALM,
    LOVED,
    SAD,
    ANGRY,
    FRUSTRATED,
    ANXIOUS,
    TIRED,
    INDIFFERENT;

    public static DiaryEmotion fromString(String emotion) {
        if (emotion == null || emotion.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid emotion");
        }

        try {
            return DiaryEmotion.valueOf(emotion.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid emotion");
        }
    }
}
