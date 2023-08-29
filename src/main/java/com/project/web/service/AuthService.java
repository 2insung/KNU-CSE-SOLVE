package com.project.web.service;

import com.project.web.domain.Member;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public String me(String userName){
        Member member = memberRepository.findByUsername(userName)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        return member.getNickname();
    }

//    @Transactional
//    public LoginRequestDto login(LoginRequestDto loginRequestDto){
//        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
//        if(!memberRepository.existsByUsername(loginRequestDto.getUsername())){
//            throw new RuntimeException("이메일을 확인해주세요.");
//        }
//
//    }
}
