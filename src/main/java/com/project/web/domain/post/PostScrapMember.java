package com.project.web.domain.post;

import com.project.web.domain.member.Member;
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

    @Column(name = "scrap_at")
    @CreationTimestamp
    private LocalDateTime scrapAt;

    @Builder
    public PostScrapMember(Integer id, Post post, Member member) {
        this.id = id;
        this.post = post;
        this.member = member;
    }
}