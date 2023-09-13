package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "post_type_id", referencedColumnName = "id")
    private PostType postType;

    private String title;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Boolean isNotification;

    private Boolean isHot;

    private Integer hits;

    private Integer recommend;

    private Integer commentCount;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    @Builder
    public Post(Member member, PostType postType, String title, LocalDateTime createdAt, LocalDateTime updatedAt,
                Boolean isNotification, Boolean isHot, Integer hits, Integer recommend , Integer commentCount){
        this.member = member;
        this.postType = postType;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isNotification = isNotification;
        this.isHot = isHot;
        this.hits = hits;
        this.recommend = recommend;
        this.commentCount = commentCount;
    }

}
