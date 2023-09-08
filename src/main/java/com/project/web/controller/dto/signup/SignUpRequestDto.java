package com.project.web.controller.dto.signup;


import com.project.web.domain.Authority;
import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
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

    public Member toMember(){
        return Member.builder()
                .nickname(nickname)
                .build();
    }

    public MemberAuth toMemberAuth(PasswordEncoder passwordEncoder, Member member){
        return MemberAuth.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Authority.ROLE_USER)
                .member(member)
                .build();
    }
}
