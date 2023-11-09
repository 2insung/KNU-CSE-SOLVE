package com.project.web.controller.community.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavePostRequestDto {
    private String boardType;
    private String title;
    private String body;
    private Boolean isNotice;
}
