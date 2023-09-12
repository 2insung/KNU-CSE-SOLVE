package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_auth")
public class MemberAuth implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String username;

    @Column(length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority role;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Builder
    public MemberAuth(Long id, String username, String password, Authority role, Member member){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.member = member;
    }

}
