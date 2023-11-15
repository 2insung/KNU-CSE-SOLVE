package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
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

    @Column(name = "title")
    @Size(max = 30)
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "summary")
    @Size(max = 50)
    private String summary;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public PostContent(Integer id, Post post, String title, String body, String summary,
                       String thumbnail) {
        this.id = id;
        this.post = post;
        this.title = title;
        this.body = body;
        this.summary = summary;
        this.thumbnail = thumbnail;
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
}
