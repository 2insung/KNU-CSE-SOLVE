package com.project.web.service.auth;

import com.project.web.domain.member.*;
import com.project.web.exception.Error404Exception;
import com.project.web.exception.SignUpException;
import com.project.web.repository.member.*;
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
    private final MemberPasswordRepository memberPasswordRepository;
    private final MemberDetailRepository memberDetailRepository;


    @Transactional
    public void signup(String username, String password, String nickname) {
        if (memberAuthRepository.existsByUsername(username)) {
            throw new Error404Exception("이미 가입되어 있는 유저입니다.");
        }

        if (memberDetailRepository.existsByNickname(nickname)) {
            throw new Error404Exception("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .isDeleted(false)
                .build();

        MemberAuth memberAuth = MemberAuth.builder()
                .member(member)
                .username(username)
                .level(Level.LEVEL_USER)
                .role(Authority.ROLE_USER)
                .build();

        MemberPassword memberPassword = MemberPassword.builder()
                .member(member)
                .password(passwordEncoder.encode(password))
                .build();


        MemberDetail memberDetail = MemberDetail.builder()
                .member(member)
                .nickname(nickname)
                .profileImage(null)
                .description(null)
                .grade(null)
                .admissionYear(null)
                .department(null)
                .build();



        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
        memberDetailRepository.save(memberDetail);
        memberPasswordRepository.save(memberPassword);

    }
}
