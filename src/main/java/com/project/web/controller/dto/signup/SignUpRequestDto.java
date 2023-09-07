package com.project.web.controller.dto.signup;


import com.project.web.domain.Authority;
import com.project.web.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder){

        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(Authority.ROLE_USER)
                .build();

    }
}
