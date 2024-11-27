package com.jygoh.heartspace.domain.media.service;

import com.jygoh.heartspace.domain.media.dto.UploadMediaDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    private static final int MAX_MEDIA_COUNT_FEED = 30;
    private static final int MAX_MEDIA_COUNT_POST = 5;
    private static final int MAX_MEDIA_COUNT_ANSWER = 5;
    private static final int MAX_MEDIA_COUNT_COMMENT = 1;

    @Override
    public String uploadOneMedia(MultipartFile file) {
        return generateMediaUrl(file);
    }

    @Override
    public List<String> uploadMediaFiles(UploadMediaDto uploadMediaDto) {

        int maxMediaCount = getMaxMediaCountForContext(uploadMediaDto.getContext());

        if (uploadMediaDto.getFiles().size() > maxMediaCount) {
            throw new IllegalArgumentException("too many files");
        }
        List<String> mediaUrls = new ArrayList<>();

        for (MultipartFile file : uploadMediaDto.getFiles()) {
            mediaUrls.add(generateMediaUrl(file));
        }
        return mediaUrls;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private int getMaxMediaCountForContext(String context) {
        return switch (context.toLowerCase()) {
            case "feed" -> MAX_MEDIA_COUNT_FEED;
            case "post" -> MAX_MEDIA_COUNT_POST;
            case "answer" -> MAX_MEDIA_COUNT_ANSWER;
            case "comment" -> MAX_MEDIA_COUNT_COMMENT;
            default -> throw new IllegalArgumentException("Invalid context: " + context);
        };
    }

    private String generateMediaUrl(MultipartFile file) {
        try {
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + "." + fileExtension;
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            return "/media/files/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file");
        }
    }
}
