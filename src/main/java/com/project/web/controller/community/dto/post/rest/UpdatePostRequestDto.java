package com.project.web.controller.community.dto.post.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePostRequestDto {
    private String boardType;
    private Integer postId;
    private Integer postAuthorId;
    private String postTitle;
    private String postBody;
    private Boolean postIsNotice;
}
