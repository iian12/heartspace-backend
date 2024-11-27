package com.jygoh.heartspace.domain.post.service;

import com.jygoh.heartspace.domain.media.model.MediaType;
import com.jygoh.heartspace.domain.media.model.PostMedia;
import com.jygoh.heartspace.domain.media.repository.PostMediaRepository;
import com.jygoh.heartspace.domain.media.service.MediaFormatExtractor;
import com.jygoh.heartspace.domain.post.dto.CreatePostReqDto;
import com.jygoh.heartspace.domain.post.model.Category;
import com.jygoh.heartspace.domain.post.model.Posts;
import com.jygoh.heartspace.domain.post.repository.PostRepository;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EncodeDecode encodeDecode;
    private final PostMediaRepository postMediaRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, EncodeDecode encodeDecode,
        PostMediaRepository postMediaRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.encodeDecode = encodeDecode;
        this.postMediaRepository = postMediaRepository;
    }

    @Override
    public String createPost(CreatePostReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        AtomicInteger counter = new AtomicInteger(0);
        if (reqDto.getMediaList().size() > 5) {
            throw new IllegalArgumentException("Too many media");
        }

        Posts newPost = Posts.builder()
            .content(reqDto.getContent())
            .title(reqDto.getTitle())
            .category(
                Category.fromString(reqDto.getCategoryName())
                    .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid category: " + reqDto.getCategoryName()))
            )
            .isAnonymous(reqDto.isAnonymous())
            .userId(user.getId())
            .build();

        postRepository.save(newPost);

        List<PostMedia> mediaList = reqDto.getMediaList().stream()
            .map(mediaDto -> {
                MediaType mediaType = MediaFormatExtractor.extractMediaType(mediaDto.getMediaUrl());
                return PostMedia.builder()
                    .postId(newPost.getId())
                    .mediaUrl(mediaDto.getMediaUrl())
                    .mediaType(mediaType)
                    .orderIndex(mediaDto.getOrderIndex() != null
                        ? mediaDto.getOrderIndex()
                        : counter.getAndIncrement())
                    .build();
            })
            .toList();

        postMediaRepository.saveAll(mediaList);

        return encodeDecode.encode(newPost.getId());
    }
}
