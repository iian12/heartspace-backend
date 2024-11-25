package com.jygoh.heartspace.domain.reply.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateReplyReqDto {

    String postId;
    String content;
}
