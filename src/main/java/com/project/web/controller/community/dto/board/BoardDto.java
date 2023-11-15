package com.project.web.controller.community.dto.board;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
public class BoardDto {
    private Integer boardId;
    private String type;
    private String alias;
    private String description;
    private Integer postCount;
    private Integer hotPostCount;

    @Builder
    public BoardDto(String type, Integer boardId, String alias, String description,
                    Integer postCount, Integer hotPostCount) {
        this.boardId = boardId;
        this.type = type;
        this.alias = alias;
        this.description = description;
        this.postCount = postCount;
        this.hotPostCount = hotPostCount;
    }
}
