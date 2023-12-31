package com.insung.knucsesolve.service.auth;

import com.insung.knucsesolve.domain.member.Member;
import com.insung.knucsesolve.domain.member.MemberAuth;
import com.insung.knucsesolve.domain.member.MemberDetail;
import com.insung.knucsesolve.domain.member.Role;
import com.insung.knucsesolve.exception.Error404Exception;
import com.insung.knucsesolve.exception.Error500Exception;
import com.insung.knucsesolve.repository.member.MemberAuthRepository;
import com.insung.knucsesolve.repository.member.MemberDetailRepository;
import com.insung.knucsesolve.repository.member.MemberRepository;
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
            Object result = memberRepository.findExistingMemberByUsername(username)
                    .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));
            Object[] arr = (Object[]) result;
            Integer resultMemberId = (Integer) arr[0];
            Boolean resultIsDeleted = (Boolean) arr[1];
            String resultUsername = (String) arr[2];
            String resultPassword = (String) arr[3];
            String resultNickname = (String) arr[4];

            if (resultIsDeleted && resultUsername.equals(username) && resultNickname.equals(nickname) && passwordEncoder.matches(password, resultPassword)) {
                if (memberRepository.updateIsDeletedById(false, resultMemberId) == 0) {
                    throw new Error500Exception("존재하지 않는 사용자입니다.");
                }
                return;
            }
            else {
                throw new Error500Exception("이미 가입되어 있는 유저입니다.");
            }
        }

        if (memberDetailRepository.existsByNickname(nickname)) {
            throw new Error500Exception("이미 존재하는 닉네임입니다.");
        }

        Member member = Member.builder()
                .isDeleted(false)
                .build();
        memberRepository.save(member);

        MemberAuth memberAuth = MemberAuth.builder()
                .member(member)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
        memberAuthRepository.save(memberAuth);

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
