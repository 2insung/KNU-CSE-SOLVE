package com.project.web.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment_recommend_count")
public class CommentRecommendCount {
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
    public CommentRecommendCount(Comment comment, Integer recommendCount) {
        this.comment = comment;
        this.recommendCount = recommendCount;
    }
}