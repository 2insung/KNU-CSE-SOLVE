package com.project.web.controller.my.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteMyCommentRequestDto {
    private Integer postId;
    private Integer commentId;
    private Integer commentAuthorId;
    private Integer currentPage;
}
