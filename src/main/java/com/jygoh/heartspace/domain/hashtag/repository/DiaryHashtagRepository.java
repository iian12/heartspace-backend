package com.jygoh.heartspace.domain.hashtag.repository;

import com.jygoh.heartspace.domain.hashtag.model.DiaryHashtag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, Long> {

    Optional<DiaryHashtag> findByUserIdAndName(Long userId, String name);
}
