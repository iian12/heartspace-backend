package com.jygoh.heartspace.domain.reply.service;

import com.jygoh.heartspace.domain.reply.dto.CreateReplyReqDto;

public interface ReplyService {

    String createReply(CreateReplyReqDto reqDto, String token);
}
