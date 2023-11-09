package com.project.web.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board_post_count")
public class BoardPostCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "post_count")
    private Integer postCount;

    @Builder
    public BoardPostCount(Board board, Integer postCount) {
        this.board = board;
        this.postCount = postCount;
    }

}
