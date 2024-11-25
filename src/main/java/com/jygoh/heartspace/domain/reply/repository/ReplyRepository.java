package com.jygoh.heartspace.domain.reply.repository;

import com.jygoh.heartspace.domain.reply.model.Replies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Replies, Long> {

}
