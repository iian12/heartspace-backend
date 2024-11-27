package com.jygoh.heartspace.domain.media.model;

import com.jygoh.heartspace.global.exception.UnsupportedMediaFormatException;

public enum MediaType {
    IMAGE,
    VIDEO,
    GIF,
    AUDIO;

    public static MediaType fromString(String mediaType) {
        return switch (mediaType.toUpperCase()) {
            case "IMAGE" -> IMAGE;
            case "VIDEO" -> VIDEO;
            case "GIF" -> GIF;
            case "AUDIO" -> AUDIO;
            default -> throw new UnsupportedMediaFormatException("Unsupported media type: " + mediaType);
        };
    }
}
