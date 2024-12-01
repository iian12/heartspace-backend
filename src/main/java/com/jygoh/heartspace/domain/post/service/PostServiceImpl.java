package com.jygoh.heartspace.domain.post.service;

import com.jygoh.heartspace.domain.hashtag.dto.PostHashtagDto;
import com.jygoh.heartspace.domain.hashtag.model.PostHashtag;
import com.jygoh.heartspace.domain.hashtag.repository.PostHashtagRepository;
import com.jygoh.heartspace.domain.media.model.MediaType;
import com.jygoh.heartspace.domain.media.model.PostMedia;
import com.jygoh.heartspace.domain.media.repository.PostMediaRepository;
import com.jygoh.heartspace.domain.media.service.MediaFormatExtractor;
import com.jygoh.heartspace.domain.media.service.MediaUtils;
import com.jygoh.heartspace.domain.post.dto.CreatePostReqDto;
import com.jygoh.heartspace.domain.post.dto.PostListResDto;
import com.jygoh.heartspace.domain.post.model.Posts;
import com.jygoh.heartspace.domain.post.model.Type;
import com.jygoh.heartspace.domain.post.repository.PostRepository;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EncodeDecode encodeDecode;
    private final PostMediaRepository postMediaRepository;
    private final PostHashtagRepository postHashtagRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
        EncodeDecode encodeDecode, PostMediaRepository postMediaRepository,
        PostHashtagRepository postHashtagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.encodeDecode = encodeDecode;
        this.postMediaRepository = postMediaRepository;
        this.postHashtagRepository = postHashtagRepository;
    }

    @Override
    public String createPost(CreatePostReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        if (reqDto.getHashtagNames().size() > 3) {
            throw new IllegalArgumentException("Too many Hashtags");
        }

        if (reqDto.getMediaList().size() > 5) {
            throw new IllegalArgumentException("Too many Media");
        }

        List<Long> hashtagIds = new ArrayList<>();

        if (reqDto.getHashtagNames() != null && !reqDto.getHashtagNames().isEmpty()) {
            for (String hashtagName : reqDto.getHashtagNames()) {
                PostHashtag hashtag = postHashtagRepository.findByName(hashtagName)
                    .orElseGet(() -> {
                        PostHashtag newHashtag = PostHashtag.builder()
                            .name(hashtagName)
                            .build();
                        return postHashtagRepository.save(newHashtag);
                    });

                hashtagIds.add(hashtag.getId());

                hashtag.updatePostCount();
                postHashtagRepository.save(hashtag);
            }
        }

        Posts newPost = Posts.builder()
            .title(reqDto.getTitle())
            .content(reqDto.getContent())
            .hashtagIds(hashtagIds)
            .type(Type.fromString(reqDto.getType()))
            .userId(user.getId())
            .build();

        postRepository.save(newPost);

        AtomicReference<String> thumbnailUrl = new AtomicReference<>();

        if (reqDto.getMediaList() != null && !reqDto.getMediaList().isEmpty()) {
            AtomicInteger counter = new AtomicInteger(0);

            List<PostMedia> mediaList = reqDto.getMediaList().stream()
                .map(mediaDto -> {
                    MediaType mediaType = MediaFormatExtractor.extractMediaType(mediaDto.getMediaUrl());

                    thumbnailUrl.set(MediaUtils.generateThumbnail(mediaType, mediaDto.getMediaUrl()));

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
        }

        if (thumbnailUrl.get() != null) {
            newPost.updateThumbnailUrl(thumbnailUrl.get());
        } else {
            newPost.updateThumbnailUrl("Default");
        }
        return encodeDecode.encode(newPost.getId());
    }

    @Override
    public List<PostListResDto> getPostList() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
            .stream()
            .map(this::createPostListResDto)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public List<PostListResDto> getPostListByType(String typeValue) {
        Type type = Type.fromString(typeValue);

        return postRepository.findByType(type)
            .stream()
            .map(this::createPostListResDto)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private PostListResDto createPostListResDto(Posts post) {
        try {
            Users author = userRepository.findById(post.getUserId())
                .orElse(null);

            if (author == null ||
                author.getProfileImgUrl() == null ||
                author.getProfileId() == null ||
                author.getNickname() == null) {
                return null;
            }

            List<PostHashtagDto> hashtags = post.getHashtagIds().stream()
                .map(this::convertToPostHashtagDto)
                .filter(Objects::nonNull)
                .toList();

            return PostListResDto.builder()
                .postId(encodeDecode.encode(post.getId()))
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .authorProfileImgUrl(author.getProfileImgUrl())
                .authorProfileId(author.getProfileId())
                .authorNickname(author.getNickname())
                .mediaExists(postMediaRepository.existsByPostId(post.getId()))
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .hashtags(hashtags)
                .createdAt(post.getCreatedAt())
                .build();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private PostHashtagDto convertToPostHashtagDto(Long hashtagId) {
        return postHashtagRepository.findById(hashtagId)
            .map(hashtag -> PostHashtagDto.builder()
            .hashtagId(encodeDecode.encode(hashtagId))
            .hashtagName(hashtag.getName())
            .build())
            .orElse(null);
    }
}
