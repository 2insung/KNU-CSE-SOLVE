package com.project.web;

import com.project.web.controller.dto.signup.SignUpRequestDto;
import com.project.web.controller.dto.signup.SignUpResponseDto;
import com.project.web.repository.MemberRepository;
import com.project.web.service.SignUpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SignUpServiceTest {
    @Autowired
    private SignUpService signUpService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private final String TEST_USERNAME = "username";
    private final String TEST_NICKNAME = "nickname";
    private final String TEST_PASSWORD = "password";

    @Test
    public void username_예외_테스트(){
        when(memberRepository.existsByUsername(TEST_USERNAME)).thenReturn(true);

        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setUsername(TEST_USERNAME);

        assertThrows(RuntimeException.class, () -> {
            signUpService.signup(requestDto);
        });
    }

    @Test
    public void nickname_예외_테스트(){
        when(memberRepository.existsByNickname(TEST_NICKNAME)).thenReturn(true);

        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setNickname(TEST_NICKNAME);

        assertThrows(RuntimeException.class, () -> {
            signUpService.signup(requestDto);
        });
    }



    @Test
    public void 회원가입_테스트(){
        when(memberRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
        when(memberRepository.existsByNickname(TEST_NICKNAME)).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setUsername(TEST_USERNAME);
        requestDto.setNickname(TEST_NICKNAME);
        requestDto.setPassword(TEST_PASSWORD);

        SignUpResponseDto responseDto = signUpService.signup(requestDto);

        assertNotNull(responseDto);

        Assertions.assertEquals(TEST_USERNAME, responseDto.getUsername());
        Assertions.assertEquals(TEST_NICKNAME, responseDto.getNickname());
    }


}
