package com.project.web.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment_child_count")
public class CommentChildCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "child_count")
    private Integer childCount;

    @Builder
    public CommentChildCount(Comment comment, Integer childCount) {
        this.comment = comment;
        this.childCount = childCount;
    }
}
