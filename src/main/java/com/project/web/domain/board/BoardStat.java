package com.project.web.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board_stat")
public class BoardStat {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "post_count")
    private Integer postCount;

    @Column(name = "hot_post_count")
    private Integer hotPostCount;

    @Builder
    public BoardStat(Integer id, Board board, Integer postCount, Integer hotPostCount) {
        this.id = id;
        this.board = board;
        this.postCount = postCount;
        this.hotPostCount = hotPostCount;
    }

}
