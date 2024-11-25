package com.jygoh.heartspace.domain.comment.service;

import com.jygoh.heartspace.domain.comment.dto.CreateCommentReqDto;
import com.jygoh.heartspace.domain.comment.model.CommentType;
import com.jygoh.heartspace.domain.comment.model.Comments;
import com.jygoh.heartspace.domain.comment.repository.CommentRepository;
import com.jygoh.heartspace.domain.post.repository.PostRepository;
import com.jygoh.heartspace.domain.reply.repository.ReplyRepository;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import com.jygoh.heartspace.global.utils.EncodeDecode;
import com.jygoh.heartspace.global.utils.EntityValidationUtils;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EncodeDecode encodeDecode;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
        EncodeDecode encodeDecode, PostRepository postRepository, ReplyRepository replyRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.encodeDecode = encodeDecode;
        this.postRepository = postRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public String createComment(CreateCommentReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        Long postId = EntityValidationUtils.decodeAndValidate(
            reqDto.getPostId(),
            encodeDecode,
            postRepository::existsById,
            "Post not found"
        );

        Long replyId = EntityValidationUtils.decodeAndValidate(
            reqDto.getReplyId(),
            encodeDecode,
            replyRepository::existsById,
            "Reply not found"
        );

        Long parentCommentId = EntityValidationUtils.decodeAndValidate(
            reqDto.getParentCommentId(),
            encodeDecode,
            this::isValidParentComment,
            "Invalid parent comment id"
        );

        CommentType commentType = (replyId != null)
            ? CommentType.REPLY_COMMENT
            : CommentType.POST_COMMENT;

        Comments comments = Comments.builder()
            .postId(postId)
            .replyId(replyId)
            .userId(user.getId())
            .commentType(commentType)
            .content(reqDto.getContent())
            .parentCommentId(parentCommentId)
            .build();

        commentRepository.save(comments);
        return encodeDecode.encode(comments.getId());
    }

    private boolean isValidParentComment(Long parentId) {
        Comments parentComment = commentRepository.findById(parentId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));
        if (parentComment.getParentCommentId() != null) {
            throw new IllegalArgumentException("Reply Comments to comments are not allowed.");
        }
        return true;
    }

}
