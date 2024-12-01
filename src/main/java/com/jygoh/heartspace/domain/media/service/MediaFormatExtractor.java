package com.jygoh.heartspace.domain.media.service;

import com.jygoh.heartspace.domain.media.model.MediaType;
import com.jygoh.heartspace.global.exception.UnsupportedMediaFormatException;

public class MediaFormatExtractor {

    public static MediaType extractMediaType(String mediaUrl) {
        if (mediaUrl == null || !mediaUrl.contains(".")) {
            throw new UnsupportedMediaFormatException("Invalid or unsupported media format");
        }

        String extension = mediaUrl.substring(mediaUrl.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "jpg", "jpeg", "png", "bmp", "webp" -> MediaType.IMAGE;
            case "mp4", "mkv", "avi", "mov", "flv" -> MediaType.VIDEO;
            case "gif" -> MediaType.GIF;
            default -> throw new UnsupportedMediaFormatException("Unsupported media format");
        };
    }
}
