package com.project.web.domain;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Long parentCommentId;

    private String body;

    @Builder
    public Comment(Member member, Post post, LocalDateTime createdAt, String body){
        this.member = member;
        this.post = post;
        this.createdAt = createdAt;
        this.body = body;
    }

}
