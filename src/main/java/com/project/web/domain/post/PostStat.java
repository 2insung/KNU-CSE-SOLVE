package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_stat")
public class PostStat {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "hit_count")
    private Integer hitCount;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "total_comment_count")
    private Integer totalCommentCount;

    @Column(name = "scrap_count")
    private Integer scrapCount;

    @Builder
    public PostStat(Integer id, Post post, Integer hitCount, Integer recommendCount, Integer commentCount,
                    Integer totalCommentCount, Integer scrapCount) {
        this.id = id;
        this.post = post;
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
        this.totalCommentCount = totalCommentCount;
        this.scrapCount = scrapCount;
    }
}