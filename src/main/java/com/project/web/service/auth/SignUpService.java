package com.project.web.service.auth;

import com.project.web.domain.member.*;
import com.project.web.exception.Error404Exception;
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

    /*
      회원가입 함수.
     * 만약 입력한 username이 이미 존재하며, 그 username을 갖고 있는 member가 탈퇴한 회원이면서 nickname, password 모두 입력된 값들과 동일하다면,
     * 이전에 탈퇴한 회원이 재가입하는 경우임.
     * 그렇지 않다면, 단순 username 중복 예외를 던짐.
     * 새로 가입할 멤버의 정보를 저장.
    */
    @Transactional
    public void signup(String username, String password, String nickname) {
        if (memberAuthRepository.existsByUsername(username)) {
            Object result = memberRepository.findUserByUsername(username)
                    .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
            Object[] arr = (Object[]) result;
            Integer resultMemberId = (Integer) arr[0];
            Boolean resultIsDeleted = (Boolean) arr[1];
            String resultUsername = (String) arr[2];
            String resultNickname = (String) arr[3];
            String resultPassword = (String) arr[4];

            if (resultIsDeleted && resultUsername.equals(username) && resultNickname.equals(nickname) && passwordEncoder.matches(password, resultPassword)) {
                if (memberRepository.updateIsDeleted(false, resultMemberId) == 0) {
                    throw new Error404Exception("존재하지 않는 사용자입니다.");
                }
                return;
            }
            else {
                throw new Error404Exception("이미 가입되어 있는 유저입니다.");
            }
        }

        if (memberDetailRepository.existsByNickname(nickname)) {
            throw new Error404Exception("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .isDeleted(false)
                .build();
        memberRepository.save(member);

        MemberAuth memberAuth = MemberAuth.builder()
                .member(member)
                .username(username)
                .role(Role.ROLE_USER)
                .build();
        memberAuthRepository.save(memberAuth);

        MemberPassword memberPassword = MemberPassword.builder()
                .member(member)
                .password(passwordEncoder.encode(password))
                .build();
        memberPasswordRepository.save(memberPassword);

        MemberDetail memberDetail = MemberDetail.builder()
                .member(member)
                .nickname(nickname)
                .profileImage(null)
                .description(null)
                .grade(null)
                .admissionYear(null)
                .department(null)
                .build();
        memberDetailRepository.save(memberDetail);
    }
}
