package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_info")
public class PostInfo {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "is_notification")
    private Boolean isNotification;

    @Column(name = "is_hot")
    private Boolean isHot;

    @Column(name = "hit_count")
    private Integer hitCount;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    @Column(name = "total_comment_count")
    private Integer totalCommentCount;

    @Column(name = "root_comment_count")
    private Integer rootCommentCount;

    @Builder
    public PostInfo(Long id, Post post, Boolean isNotification, Boolean isHot,
                    Integer hitCount, Integer recommendCount, Integer totalCommentCount,
                    Integer rootCommentCount) {
        this.id = id;
        this.post = post;
        this.isNotification = isNotification;
        this.isHot = isHot;
        this.hitCount = hitCount;
        this.recommendCount = recommendCount;
        this.totalCommentCount = totalCommentCount;
        this.rootCommentCount = rootCommentCount;
    }

}
