package com.jygoh.heartspace.domain.diary.service;

import com.jygoh.heartspace.domain.diary.dto.CreateDiaryReqDto;
import com.jygoh.heartspace.domain.diary.model.Diary;
import com.jygoh.heartspace.domain.diary.model.DiaryEmotion;
import com.jygoh.heartspace.domain.diary.repository.DiaryRepository;
import com.jygoh.heartspace.domain.hashtag.model.DiaryHashtag;
import com.jygoh.heartspace.domain.hashtag.repository.DiaryHashtagRepository;
import com.jygoh.heartspace.domain.media.model.DiaryMedia;
import com.jygoh.heartspace.domain.media.model.MediaType;
import com.jygoh.heartspace.domain.media.repository.DiaryMediaRepository;
import com.jygoh.heartspace.domain.media.service.MediaFormatExtractor;
import com.jygoh.heartspace.domain.media.service.MediaUtils;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;
    private final EncodeDecode encodeDecode;
    private final DiaryMediaRepository diaryMediaRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository, UserRepository userRepository,
        DiaryHashtagRepository diaryHashtagRepository, EncodeDecode encodeDecode,
        DiaryMediaRepository diaryMediaRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
        this.diaryHashtagRepository = diaryHashtagRepository;
        this.encodeDecode = encodeDecode;
        this.diaryMediaRepository = diaryMediaRepository;
    }

    @Override
    public String createDiary(CreateDiaryReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        if (reqDto.getHashtagNames().size() > 3) {
            throw new IllegalArgumentException("Too many Hashtags");
        }

        if (reqDto.getMediaList().size() > 5) {
            throw new IllegalArgumentException("Too many Media");
        }

        List<Long> hashtagIds = new ArrayList<>();

        if (reqDto.getHashtagNames() != null && !reqDto.getHashtagNames().isEmpty()) {
            reqDto.getHashtagNames().forEach(name -> {
                DiaryHashtag hashtag = diaryHashtagRepository.findByUserIdAndName(user.getId(), name)
                    .orElseGet(() -> {
                        DiaryHashtag newHashtag = DiaryHashtag.builder()
                            .userId(user.getId())
                            .name(name)
                            .build();
                        return diaryHashtagRepository.save(newHashtag);
                    });

                hashtag.updateDiaryCount();
                diaryHashtagRepository.save(hashtag);

                hashtagIds.add(hashtag.getId());
            });
        }

        Diary newDiary = Diary.builder()
            .userId(user.getId())
            .title(reqDto.getTitle())
            .content(reqDto.getTitle())
            .date(reqDto.getDate())
            .emotion(DiaryEmotion.fromString(reqDto.getEmotion()))
            .hashtagIds(hashtagIds)
            .build();

        diaryRepository.save(newDiary);

        AtomicReference<String> thumbnailUrl = new AtomicReference<>();

        if (reqDto.getMediaList() != null && !reqDto.getMediaList().isEmpty() ) {
            AtomicInteger counter = new AtomicInteger(0);

            List<DiaryMedia> mediaList = reqDto.getMediaList().stream()
                .map(mediaDto -> {
                    MediaType mediaType = MediaFormatExtractor.extractMediaType(mediaDto.getMediaUrl());

                    thumbnailUrl.set(MediaUtils.generateThumbnail(mediaType, mediaDto.getMediaUrl()));

                    return DiaryMedia.builder()
                        .diaryId(newDiary.getId())
                        .mediaUrl(mediaDto.getMediaUrl())
                        .mediaType(mediaType)
                        .orderIndex(mediaDto.getOrderIndex() != null
                            ? mediaDto.getOrderIndex()
                            : counter.getAndIncrement())
                        .build();
                })
                .toList();
            diaryMediaRepository.saveAll(mediaList);
        }

        if (thumbnailUrl.get() != null) {
            newDiary.updateThumbnailUrl(thumbnailUrl.get());
        } else {
            newDiary.updateThumbnailUrl("Default");
        }

        return encodeDecode.encode(newDiary.getId());
    }
}
