package com.jygoh.heartspace.domain.post.service;

import com.jygoh.heartspace.domain.post.dto.CreatePostReqDto;
import com.jygoh.heartspace.domain.post.dto.PostListResDto;
import java.util.List;

public interface PostService {

    String createPost(CreatePostReqDto reqDto, String token);

    List<PostListResDto> getPostList();

    List<PostListResDto> getPostListByType(String typeValue);

}
