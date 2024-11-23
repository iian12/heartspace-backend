package com.jygoh.heartspace.domain.post.repository;

import com.jygoh.heartspace.domain.post.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

}
