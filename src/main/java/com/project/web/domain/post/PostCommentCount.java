package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_comment_count")
public class PostCommentCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "total_comment_count")
    private Integer totalCommentCount;

    @Builder
    public PostCommentCount(Post post, Integer commentCount, Integer totalCommentCount) {
        this.post = post;
        this.totalCommentCount = totalCommentCount;
        this.commentCount = commentCount;
    }
}
