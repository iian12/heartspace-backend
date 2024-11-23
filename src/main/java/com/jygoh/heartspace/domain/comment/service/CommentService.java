package com.jygoh.heartspace.domain.comment.service;

import com.jygoh.heartspace.domain.comment.dto.CreateCommentReqDto;

public interface CommentService {

    String createComment(CreateCommentReqDto reqDto, String token);


}
