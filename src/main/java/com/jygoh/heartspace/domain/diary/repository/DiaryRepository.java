package com.jygoh.heartspace.domain.diary.repository;

import com.jygoh.heartspace.domain.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
