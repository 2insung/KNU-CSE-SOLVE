package com.project.web.controller.community.dto.board.view;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDto {
    // domain : Board
    private final Integer id;
    private final String type;
    private final String alias;
    private final String description;
    private final Integer postCount;
    private final Integer hotPostCount;

    @Builder
    public BoardDto(Integer id, String type, String alias, String description, Integer postCount,
                    Integer hotPostCount) {
        this.id = id;
        this.type = type;
        this.alias = alias;
        this.description = description;
        this.postCount = postCount;
        this.hotPostCount = hotPostCount;
    }
}
