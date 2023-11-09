package com.project.web.controller.community.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveCommentRequestDto {
    private Integer postId;
    private Integer parentCommentId;
    private Integer currentPageNumber;
    private String body;
}
