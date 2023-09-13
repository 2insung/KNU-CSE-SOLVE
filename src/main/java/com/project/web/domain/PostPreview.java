package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_preview")
public class PostPreview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    private String summary;

    private String thumbnail;

    @Builder
    public PostPreview(Post post, String summary, String thumbnail){
        this.post = post;
        this.summary = summary;
        this.thumbnail = thumbnail;
    }
}
