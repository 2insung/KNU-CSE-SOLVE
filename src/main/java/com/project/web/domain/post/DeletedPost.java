package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "deleted_post")
public class DeletedPost {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "board_id")
    private Integer boardId;

    @Column(name = "number")
    private Integer number;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "hit_count")
    private Integer hitCount;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    @Column(name = "is_notice")
    private Boolean isNotice;

    @Column(name = "is_hot")
    private Boolean isHot;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public DeletedPost(Post post, PostContent postContent, PostHitCount postHitCount, PostRecommendCount postRecommendCount) {
        this.postId = post.getId();
        this.memberId = post.getMember().getId();
        this.boardId = post.getBoard().getId();
        this.title = postContent.getTitle();
        this.body = postContent.getBody();
        this.hitCount = postHitCount.getHitCount();
        this.recommendCount = postRecommendCount.getRecommendCount();
        this.isNotice = post.getIsNotice();
        this.isHot = post.getIsHot();
    }
}
