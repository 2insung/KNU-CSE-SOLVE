package com.project.web.controller.community.dto.post;

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
    private String title;
    private String body;
    private Boolean isNotice;
}
