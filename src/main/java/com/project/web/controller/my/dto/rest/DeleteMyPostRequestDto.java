package com.project.web.controller.my.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteMyPostRequestDto {
    private Integer boardId;
    private Integer postId;
    private Integer postAuthorId;
    private Integer currentPageNumber;
}
