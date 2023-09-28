package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board_access")
public class BoardAccess {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Column(name = "user_access", nullable = false)
    private Boolean userAccess;

    @Builder
    public BoardAccess(Long id, Board board, Boolean userAccess) {
        this.id = id;
        this.board = board;
        this.userAccess = userAccess;
    }
}
