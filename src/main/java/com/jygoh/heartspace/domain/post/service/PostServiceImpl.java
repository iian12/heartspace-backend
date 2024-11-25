package com.jygoh.heartspace.domain.post.service;

import com.jygoh.heartspace.domain.post.dto.CreatePostReqDto;
import com.jygoh.heartspace.domain.post.model.Category;
import com.jygoh.heartspace.domain.post.model.Posts;
import com.jygoh.heartspace.domain.post.repository.PostRepository;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EncodeDecode encodeDecode;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, EncodeDecode encodeDecode) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.encodeDecode = encodeDecode;
    }

    @Override
    public String createPost(CreatePostReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        Posts newPost = Posts.builder()
            .title(reqDto.getTitle())
            .content(reqDto.getContent())
            .category(
                Category.fromString(reqDto.getCategoryName())
                    .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid category: " + reqDto.getCategoryName()))
            )
            .isAnonymous(reqDto.isAnonymous())
            .userId(user.getId())
            .build();

        postRepository.save(newPost);

        return encodeDecode.encode(newPost.getId());
    }
}
