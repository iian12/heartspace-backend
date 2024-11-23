package com.jygoh.heartspace.domain.comment.service;

import com.jygoh.heartspace.domain.comment.dto.CreateCommentReqDto;
import com.jygoh.heartspace.domain.comment.model.CommentType;
import com.jygoh.heartspace.domain.comment.model.Comments;
import com.jygoh.heartspace.domain.comment.repository.CommentRepository;
import com.jygoh.heartspace.domain.user.model.Users;
import com.jygoh.heartspace.domain.user.repository.UserRepository;
import com.jygoh.heartspace.global.security.utils.EncodeDecode;
import com.jygoh.heartspace.global.security.utils.TokenUtils;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EncodeDecode encodeDecode;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
        EncodeDecode encodeDecode) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.encodeDecode = encodeDecode;
    }

    @Override
    public String createComment(CreateCommentReqDto reqDto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

        Long parentCommentId = reqDto.getParentCommentId() != null
            ? validateParentComment(encodeDecode.decode(reqDto.getParentCommentId()))
            : null;

        CommentType commentType = (reqDto.getReplyId() != null) ? CommentType.REPLY_COMMENT : CommentType.POST_COMMENT;


        Comments comments = Comments.builder()
            .postId(encodeDecode.decode(reqDto.getPostId()))
            .replyId(reqDto.getReplyId() != null ? encodeDecode.decode(reqDto.getReplyId()) : null)
            .userId(user.getId())
            .commentType(commentType)
            .content(reqDto.getContent())
            .parentCommentId(parentCommentId)
            .build();

        commentRepository.save(comments);
        return encodeDecode.encode(comments.getId());
    }

    private Long validateParentComment(Long parentId) {
        if (parentId == null) {
            return null;
        }

        Comments parentComment = commentRepository.findById(parentId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));

        if (parentComment.getParentCommentId() != null) {
            throw new IllegalArgumentException("Reply Comments to comments are not allowed.");
        }

        return parentId;
    }
}
