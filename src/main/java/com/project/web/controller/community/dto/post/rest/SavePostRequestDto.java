package com.project.web.controller.community.dto.post.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavePostRequestDto {
    private String boardType;
    private String postTitle;
    private String postBody;
    private Boolean postIsNotice;
}
