package com.insung.knucsesolve.domain.post;

import com.insung.knucsesolve.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_scrap_member")
public class PostScrapMember {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "scrapped_at")
    @CreationTimestamp
    private LocalDateTime scrappedAt;

    @Builder
    public PostScrapMember(Integer id, Post post, Member member) {
        this.id = id;
        this.post = post;
        this.member = member;
    }
}