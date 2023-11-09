package com.project.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "signup_code")
public class SignUpCode {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    @Size(max = 50)
    private String username;

    @Column(name = "code")
    private String code;

    @Builder
    public SignUpCode(String username, String code){
        this.username = username;
        this.code = code;
    }

    public void updateCode(String code){
        this.code = code;
    }
}
