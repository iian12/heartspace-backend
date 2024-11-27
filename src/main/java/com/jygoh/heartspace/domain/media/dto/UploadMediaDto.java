package com.jygoh.heartspace.domain.media.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class UploadMediaDto {

    private String context;
    private List<MultipartFile> files;

    @Builder
    public UploadMediaDto(String context, List<MultipartFile> files) {
        this.context = context;
        this.files = files;
    }
}
