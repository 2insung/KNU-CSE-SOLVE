package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_hit_count")
public class PostHitCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "hit_count")
    private Integer hitCount;

    @Builder
    public PostHitCount(Post post, Integer hitCount) {
        this.post = post;
        this.hitCount = hitCount;
    }
}
