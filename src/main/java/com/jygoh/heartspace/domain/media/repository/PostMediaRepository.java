package com.jygoh.heartspace.domain.media.repository;

import com.jygoh.heartspace.domain.media.model.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    int countByPostId(Long postId);

    boolean existsByPostId(Long postId);

    boolean existsByPostIdAndOrderIndex(Long postId, int orderIndex);
}
