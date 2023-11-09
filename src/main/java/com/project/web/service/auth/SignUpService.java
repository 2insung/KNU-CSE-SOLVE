package com.project.web.service.auth;

import com.project.web.domain.member.*;
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
    private final MemberPostCountRepository memberPostCountRepository;
    private final MemberCommentCountRepository memberCommentCountRepository;

    @Transactional
    public void signup(String username, String password, String nickname) {
        if (memberAuthRepository.existsByUsername(username)) {
            throw new SignUpException("이미 가입되어 있는 유저입니다.");
        }

        if (memberDetailRepository.existsByNickname(nickname)) {
            throw new SignUpException("이미 존재하는 닉네임입니다.");
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

        MemberPostCount memberPostCount = MemberPostCount.builder()
                .member(member)
                .postCount(0)
                .build();

        MemberCommentCount memberCommentCount = MemberCommentCount.builder()
                .member(member)
                .commentCount(0)
                .build();

        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
        memberDetailRepository.save(memberDetail);
        memberPostCountRepository.save(memberPostCount);
        memberCommentCountRepository.save(memberCommentCount);
        memberPasswordRepository.save(memberPassword);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingUsername(String username) {
        return memberAuthRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Boolean checkExistingNickname(String nickname) {
        return memberDetailRepository.existsByNickname(nickname);
    }
}
