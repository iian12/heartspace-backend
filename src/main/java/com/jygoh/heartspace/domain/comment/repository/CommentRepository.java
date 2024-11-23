package com.jygoh.heartspace.domain.comment.repository;

import com.jygoh.heartspace.domain.comment.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

}
