package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_member_id")
    private Member parentMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_comment_id")
    private Comment rootComment;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "body")
    @Size(max = 500)
    private String body;

    @Column(name = "children_count")
    private Integer childrenCount;

    @Column(name = "root_comment_order")
    private Integer rootCommentOrder;

    @Column(name = "children_comment_order")
    private Integer childrenCommentOrder;

    @Column(name = "is_parent_root")
    private Boolean isParentRoot;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Builder
    public Comment(Long id, Member member, Member parentMember, Post post, Comment rootComment,
                   LocalDateTime createdAt, String body, Integer childrenCount, Integer rootCommentOrder,
                   Integer childrenCommentOrder, Boolean isParentRoot, Boolean isDelete) {
        this.id = id;
        this.member = member;
        this.parentMember = parentMember;
        this.post = post;
        this.rootComment = rootComment;
        this.createdAt = createdAt;
        this.body = body;
        this.childrenCount = childrenCount;
        this.rootCommentOrder = rootCommentOrder;
        this.childrenCommentOrder = childrenCommentOrder;
        this.isParentRoot = isParentRoot;
        this.isDelete = isDelete;
    }

}
