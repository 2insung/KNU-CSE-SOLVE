package com.project.web.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

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
    @Size(max = 50)
    private String type;

    @Column(name = "alias")
    @Size(max = 50)
    private String alias;

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @Column(name = "category")
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
