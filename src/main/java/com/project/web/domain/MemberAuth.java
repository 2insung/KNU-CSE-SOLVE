package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_auth")
public class MemberAuth implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "password", nullable = false)
    @Size(max = 200)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Authority role;


    @Builder
    public MemberAuth(Long id, Member member, String password, Authority role) {
        this.id = id;
        this.member = member;
        this.password = password;
        this.role = role;
    }

}
