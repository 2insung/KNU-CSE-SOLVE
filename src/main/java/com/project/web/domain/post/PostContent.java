package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_content")
public class PostContent {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public PostContent(Integer id, Post post, String title, String body, String summary,
                       String thumbnail, LocalDateTime updatedAt) {
        this.id = id;
        this.post = post;
        this.title = title;
        this.body = body;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.updatedAt = updatedAt;
    }

    public void updateTitle(String title){
        this.title = title;
    }
    public void updateBody(String body){
        this.body = body;
    }
    public void updateSummary(String summary){
        this.summary = summary;
    }
    public void updateThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }
    public void updateUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
