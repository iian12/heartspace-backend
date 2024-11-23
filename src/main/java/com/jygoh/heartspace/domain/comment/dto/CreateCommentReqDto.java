package com.jygoh.heartspace.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentReqDto {

    private String postId;
    private String replyId;
    private String content;
    private String parentCommentId;

    @Builder
    public CreateCommentReqDto(String postId, String replyId, String content, String parentCommentId) {
        this.postId = postId;
        this.replyId = replyId;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
}
