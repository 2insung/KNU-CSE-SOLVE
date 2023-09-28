package com.project.web.controller.dto.signup;


import com.project.web.domain.*;
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
}
