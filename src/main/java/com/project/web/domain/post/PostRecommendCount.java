package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_recommend_count")
public class PostRecommendCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    @Builder
    public PostRecommendCount(Integer id, Post post, Integer recommendCount) {
        this.id = id;
        this.post = post;
        this.recommendCount = recommendCount;
    }
}
