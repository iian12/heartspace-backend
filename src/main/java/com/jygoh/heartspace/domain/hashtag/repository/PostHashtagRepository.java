package com.jygoh.heartspace.domain.hashtag.repository;

import com.jygoh.heartspace.domain.hashtag.model.PostHashtag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {

    Optional<PostHashtag> findByName(String hashtagName);
}
