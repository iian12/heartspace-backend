package com.jygoh.heartspace.domain.post.service;

import com.jygoh.heartspace.domain.post.dto.CreatePostReqDto;

public interface PostService {

    String createComment(CreatePostReqDto reqDto, String token);
}