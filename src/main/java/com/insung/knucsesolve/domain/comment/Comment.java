package com.insung.knucsesolve.domain.comment;

import com.insung.knucsesolve.domain.member.Member;
import com.insung.knucsesolve.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_member_id")
    private Member parentMember;

    @Column(name = "root_comment_id")
    private Integer rootCommentId;

    @Column(name = "is_post_author")
    private Boolean isPostAuthor;

    @Column(name = "is_root")
    private Boolean isRoot;

    @Column(name = "is_root_child")
    private Boolean isRootChild;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "group_created_at")
    private LocalDateTime groupCreatedAt;

    @Column(name = "child_count")
    private Integer childCount;

    @Builder
    public Comment(Integer id, Member member, Post post, Member parentMember, Integer rootCommentId,
                   Boolean isPostAuthor, Boolean isRoot, Boolean isRootChild, Boolean isDeleted, String body,
                   LocalDateTime createdAt, LocalDateTime groupCreatedAt, Integer childCount) {
        this.id = id;
        this.member = member;
        this.post = post;
        this.parentMember = parentMember;
        this.rootCommentId = rootCommentId;
        this.isPostAuthor = isPostAuthor;
        this.isRoot = isRoot;
        this.isRootChild = isRootChild;
        this.isDeleted = isDeleted;
        this.body = body;
        this.createdAt = createdAt;
        this.groupCreatedAt = groupCreatedAt;
        this.childCount = childCount;
    }

    @PostPersist
    public void setRootCommentAfterPersist() {
        if (this.rootCommentId == null) {
            this.rootCommentId = this.id;
        }

        if (this.groupCreatedAt == null) {
            this.groupCreatedAt = this.createdAt;
        }
    }
}
