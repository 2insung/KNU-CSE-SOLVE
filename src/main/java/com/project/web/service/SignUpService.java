package com.project.web.service;

import com.project.web.controller.dto.signup.SignUpRequestDto;
import com.project.web.controller.dto.signup.SignUpResponseDto;
import com.project.web.domain.Member;
import com.project.web.exception.SignUpException;
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

    @Transactional
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new SignUpException("이미 가입되어 있는 유저입니다.");
        }
        if (memberRepository.existsByNickname(signUpRequestDto.getNickname())) {
            throw new SignUpException("이미 존재하는 닉네임입니다.");
        }
        Member member = signUpRequestDto.toMember(passwordEncoder);
        memberRepository.save(member);

        return SignUpResponseDto.of(member);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingNickname(String nickname) {
        return !memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingUsername(String username) {
        return !memberRepository.existsByUsername(username);
    }
}
