package com.jygoh.heartspace.domain.diary.service;

import com.jygoh.heartspace.domain.diary.dto.CreateDiaryReqDto;
import com.jygoh.heartspace.domain.diary.model.Diary;

public interface DiaryService {

    String createDiary(CreateDiaryReqDto reqDto, String token);
}
