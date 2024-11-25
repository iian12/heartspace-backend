package com.jygoh.heartspace.domain.reply.service;

import com.jygoh.heartspace.domain.post.repository.PostRepository;
import com.jygoh.heartspace.domain.reply.dto.CreateReplyReqDto;
import com.jygoh.heartspace.domain.reply.model.Replies;
import com.jygoh.heartspace.domain.reply.repository.ReplyRepository;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import com.jygoh.heartspace.global.utils.EntityValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final EncodeDecode encodeDecode;

    public ReplyServiceImpl(PostRepository postRepository, UserRepository userRepository, ReplyRepository replyRepository,
        EncodeDecode encodeDecode) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
        this.encodeDecode = encodeDecode;
    }

    @Override
    public String createReply(CreateReplyReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        Long postId = EntityValidationUtils.decodeAndValidate(
            reqDto.getPostId(),
            encodeDecode,
            postRepository::existsById,
            "Post not found"
        );

        Replies reply = Replies.builder()
            .postId(postId)
            .userId(user.getId())
            .content(reqDto.getContent())
            .build();

        replyRepository.save(reply);
        return encodeDecode.encode(reply.getId());
    }
}
