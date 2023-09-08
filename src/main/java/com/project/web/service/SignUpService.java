package com.project.web.service;

import com.project.web.controller.dto.signup.SignUpRequestDto;
import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import com.project.web.exception.SignUpException;
import com.project.web.repository.MemberAuthRepository;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SignUpService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberAuthRepository memberAuthRepository;

    @Transactional
    public void signup(SignUpRequestDto signUpRequestDto) {
        if (memberAuthRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new SignUpException("이미 가입되어 있는 유저입니다.");
        }
        if (memberRepository.existsByNickname(signUpRequestDto.getNickname())) {
            throw new SignUpException("이미 존재하는 닉네임입니다.");
        }
        Member member = signUpRequestDto.toMember();
        MemberAuth memberAuth = signUpRequestDto.toMemberAuth(passwordEncoder, member);
        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingNickname(String nickname) {
        return !memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingUsername(String username) {
        return !memberAuthRepository.existsByUsername(username);
    }
}
