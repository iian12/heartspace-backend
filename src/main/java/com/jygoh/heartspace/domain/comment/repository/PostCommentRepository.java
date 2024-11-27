package com.jygoh.heartspace.domain.comment.repository;

import com.jygoh.heartspace.domain.comment.model.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComments, Long> {

}
