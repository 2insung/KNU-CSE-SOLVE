package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board_detail")
public class BoardDetail {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "alias")
    @Size(max = 20)
    private String alias;

    @Column(name = "description")
    @Size(max = 50)
    private String description;


    @Builder
    public BoardDetail(Long id, Board board, String alias, String description) {
        this.id = id;
        this.board = board;
        this.alias = alias;
        this.description = description;
    }
}
