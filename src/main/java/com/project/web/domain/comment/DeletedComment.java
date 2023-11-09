package com.project.web.domain.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "deleted_comment")
public class DeletedComment {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "parent_member_id")
    private Integer parentMemberId;

    @Column(name = "root_comment_id")
    private Integer rootCommentId;

    @Column(name = "is_root")
    private Boolean isRoot;

    @Column(name = "is_root_child")
    private Boolean isRootChild;

    @Column(name = "body")
    @Size(max = 500)
    private String body;

    @Column(name = "recommend_count")
    private Integer recommendCount;

//    @Column(name = "created_at")
//    private LocalDateTime createdAt;

    public DeletedComment(Comment comment, CommentRecommendCount commentRecommendCount) {
        this.commentId = comment.getId();
        this.memberId = comment.getMember().getId();
        this.postId = comment.getPost().getId();
        this.parentMemberId = comment.getParentMember().getId();
        this.rootCommentId = comment.getRootCommentId();
        this.isRoot = comment.getIsRoot();
        this.isRootChild = comment.getIsRootChild();
        this.body = comment.getBody();
        this.recommendCount = commentRecommendCount.getRecommendCount();
//        this.createdAt = comment.getCreatedAt();
    }
}
