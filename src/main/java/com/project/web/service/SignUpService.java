package com.project.web.service;

import com.project.web.controller.dto.signup.SignUpRequestDto;
import com.project.web.domain.*;
import com.project.web.exception.SignUpException;
import com.project.web.repository.*;
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
    private final MemberProfileRepository memberProfileRepository;
    private final MemberDetailRepository memberDetailRepository;

    @Transactional
    public void signup(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new SignUpException("이미 가입되어 있는 유저입니다.");
        }
        if (memberProfileRepository.existsByNickname(signUpRequestDto.getNickname())) {
            throw new SignUpException("이미 존재하는 닉네임입니다.");
        }
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        String nickname = signUpRequestDto.getNickname();

        Member member = Member.builder()
                .username(username)
                .level(Level.LEVEL_USER)
                .build();
        MemberAuth memberAuth = MemberAuth.builder()
                .password(passwordEncoder.encode(password))
                .role(Authority.ROLE_USER)
                .member(member)
                .build();
        MemberProfile memberProfile = MemberProfile.builder()
                .nickname(nickname)
                .profileImage(null)
                .postCount(0)
                .commentCount(0)
                .member(member)
                .build();
        MemberDetail memberDetail = MemberDetail.builder()
                .description(null)
                .grade(null)
                .admissionYear(null)
                .member(member)
                .build();

        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
        memberProfileRepository.save(memberProfile);
        memberDetailRepository.save(memberDetail);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingNickname(String nickname) {
        return memberProfileRepository.existsByNickname(nickname);
    }
}
