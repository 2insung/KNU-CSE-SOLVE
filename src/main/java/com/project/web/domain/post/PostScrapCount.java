package com.project.web.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_scrap_count")
public class PostScrapCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "scrap_count")
    private Integer scrapCount;

    @Builder
    public PostScrapCount(Integer id, Post post, Integer scrapCount) {
        this.id = id;
        this.post = post;
        this.scrapCount = scrapCount;
    }
}
