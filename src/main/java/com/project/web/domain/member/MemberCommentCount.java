package com.project.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_comment_count")
public class MemberCommentCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Builder
    public MemberCommentCount(Member member, Integer commentCount) {
        this.member = member;
        this.commentCount = commentCount;
    }

}
