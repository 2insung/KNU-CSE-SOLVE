package com.project.web.controller.community.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeletePostRequestDto {
    private String boardType;
    private Integer postId;
}
