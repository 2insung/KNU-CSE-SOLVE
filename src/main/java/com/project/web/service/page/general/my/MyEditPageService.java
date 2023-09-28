package com.project.web.service.page.general.my;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.controller.dto.my.MyEditPageResponseDto;
import com.project.web.domain.Member;
import com.project.web.domain.MemberDetail;
import com.project.web.domain.MemberProfile;
import com.project.web.exception.Error403Exception;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class MyEditPageService {
    private final MemberRepository memberRepository;

    public MyEditPageResponseDto getMyEditPageInfo(String nickname) {
        Object[] result = memberRepository.findByNicknameWithProfileAndDetail(nickname)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        Member member = (Member) result[0];
        MemberProfile memberProfile = (MemberProfile) result[1];
        MemberDetail memberDetail = (MemberDetail) result[2];

        return MyEditPageResponseDto.builder()
                .member(member)
                .memberProfile(memberProfile)
                .memberDetail(memberDetail)
                .build();
    }

}
