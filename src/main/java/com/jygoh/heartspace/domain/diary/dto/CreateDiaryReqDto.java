package com.jygoh.heartspace.domain.diary.dto;

import com.jygoh.heartspace.domain.media.dto.MediaDto;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateDiaryReqDto {

    private String title;
    private String content;
    private String emotion;
    private LocalDate date;
    private List<String> hashtagNames;
    private List<MediaDto> mediaList;

    @Builder
    public CreateDiaryReqDto(String title, String content, String emotion, LocalDate date, List<String> hashtagNames, List<MediaDto> mediaList) {
        this.title = title;
        this.content = content;
        this.emotion = emotion;
        this.date = date;
        this.hashtagNames = hashtagNames;
        this.mediaList = mediaList;
    }
}
