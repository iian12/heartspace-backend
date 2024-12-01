package com.jygoh.heartspace.domain.media.service;

import com.jygoh.heartspace.domain.media.model.MediaType;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MediaUtils {

    public static String generateThumbnail(MediaType mediaType, String mediaUrl) {
        switch (mediaType) {
            case VIDEO -> {
                return generateVideoThumbnail(mediaUrl);
            }
            case GIF -> {
                return generateGifThumbnail(mediaUrl);
            }
        }
        return mediaUrl;
    }

    private static String generateVideoThumbnail(String videoUrl) {

        String thumbnailPath = "/uploads/" + UUID.randomUUID() + ".jpg";

        try {
            String ffmpegCommand = "ffmpeg -i " + videoUrl + " -vf \"thumbnail,scale=320:240\" -frames:v 1" + thumbnailPath;
            Process process = Runtime.getRuntime().exec(ffmpegCommand);
            process.waitFor();
        } catch (Exception e) {
            log.error("Failed to generate video thumbnail");
        }

        return thumbnailPath;
    }

    private static String generateGifThumbnail(String gifUrl) {
        String thumbnailPath = "/uploads/" + UUID.randomUUID() + ".jpg";

        try {
            String gifToImageCommand = "convert " + gifUrl + "[0] " + thumbnailPath;
            Process process = Runtime.getRuntime().exec(gifToImageCommand);
            process.waitFor();
        } catch (Exception e) {
            log.error("Failed to generate gif thumbnail");
        }

        return thumbnailPath;
    }

}
