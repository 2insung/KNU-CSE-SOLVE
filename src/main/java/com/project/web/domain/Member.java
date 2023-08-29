package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String username;

    @Column(length = 200)
    private String password;
    @Column(unique = true, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority role;

    @Builder
    public Member(String username, String password, String nickname, Authority role){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

}
