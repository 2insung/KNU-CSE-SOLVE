package com.insung.knucsesolve.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment_stat")
public class CommentStat {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    @Builder
    public CommentStat(Integer id, Comment comment, Integer recommendCount) {
        this.id = id;
        this.comment = comment;
        this.recommendCount = recommendCount;
    }
}