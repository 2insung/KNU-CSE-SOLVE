package com.project.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_post_count")
public class MemberPostCount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "post_count")
    private Integer postCount;

    @Builder
    public MemberPostCount(Member member, Integer postCount) {
        this.member = member;
        this.postCount = postCount;
    }

}
