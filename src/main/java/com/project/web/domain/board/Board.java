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
    @Size(max = 20)
    private String type;

    @Column(name = "alias")
    @Size(max = 20)
    private String alias;

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @Column(name = "user_access", nullable = false)
    private Boolean userAccess;

    @Builder
    public Board(String type, String alias, String description, Boolean userAccess) {
        this.type = type;
        this.alias = alias;
        this.description = description;
        this.userAccess = userAccess;
    }
}
