package com.project.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_auth")
public class MemberAuth {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "username", unique = true, nullable = false)
    @Size(max = 50)
    private String username;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Authority role;

    @Builder
    public MemberAuth(Integer id, Member member, String username, Level level, Authority role) {
        this.id = id;
        this.member = member;
        this.username = username;
        this.level = level;
        this.role = role;
    }

}
