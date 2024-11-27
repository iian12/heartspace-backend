package com.jygoh.heartspace.domain.comment.repository;

import com.jygoh.heartspace.domain.comment.model.FeedComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComments, Long> {

}
