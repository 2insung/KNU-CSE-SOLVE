package com.project.web.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", unique = true, nullable = false)
    private String type;

    @Column(name = "alias", unique = true, nullable = false)
    private String alias;

    @Column(name = "description")
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Builder
    public Board(Integer id, String type, String alias, String description, String category) {
        this.id = id;
        this.type = type;
        this.alias = alias;
        this.description = description;
        this.category = category;
    }
}
