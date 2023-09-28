package com.project.web.domain;

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
@Table(name = "member_profile")
public class MemberProfile {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "nickname", nullable = false, unique = true)
    @Size(max = 20)
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "post_count")
    private Integer postCount;

    @Column(name = "comment_count")
    private Integer commentCount;


    @Builder
    public MemberProfile(Long id,  Member member, String nickname, String profileImage, Integer postCount,
                         Integer commentCount) {
        this.id = id;
        this.member = member;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.postCount = postCount;
        this.commentCount = commentCount;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}
