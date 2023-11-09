package com.project.web.controller.community.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteCommentRequestDto {
    private Integer postId;
    private Integer commentId;
    private Integer currentPageNumber;
}
