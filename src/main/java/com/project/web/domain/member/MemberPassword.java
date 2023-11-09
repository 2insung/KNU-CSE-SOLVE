package com.project.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member_password")
public class MemberPassword {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "password", nullable = false)
    @Size(max = 200)
    private String password;

    @Builder
    public MemberPassword(Member member, String password) {
        this.member = member;
        this.password = password;
    }

}
