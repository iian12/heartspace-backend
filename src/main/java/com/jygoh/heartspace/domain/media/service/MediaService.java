package com.jygoh.heartspace.domain.media.service;

import com.jygoh.heartspace.domain.media.dto.UploadMediaDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    String uploadOneMedia(MultipartFile file);

    List<String> uploadMediaFiles(UploadMediaDto uploadMediaDto);

    String generateMediaThumbnail(String mediaUrl);
}
