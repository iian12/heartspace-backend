package com.jygoh.heartspace.domain.media.controller;

import com.jygoh.heartspace.domain.media.dto.UploadMediaDto;
import com.jygoh.heartspace.domain.media.service.MediaService;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadMediaFiles(@RequestBody UploadMediaDto uploadMediaDto) {
        try {
            List<String> mediaUrls = mediaService.uploadMediaFiles(uploadMediaDto);
            return ResponseEntity.ok(mediaUrls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}
