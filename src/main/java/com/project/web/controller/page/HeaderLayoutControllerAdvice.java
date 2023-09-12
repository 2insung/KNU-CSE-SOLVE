package com.project.web.controller.page;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.controller.page.RootPageController;
import com.project.web.controller.page.SignUpPageController;
import com.project.web.domain.Member;
import com.project.web.domain.MemberProfile;
import com.project.web.repository.MemberProfileRepository;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes =
        {RootPageController.class,
                SignUpPageController.class,
                MyPageController.class,
        BoardPageController.class,
        WritePageController.class})
@RequiredArgsConstructor
public class HeaderLayoutControllerAdvice {
    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;

    @ModelAttribute("headerIsLogin")
    public Boolean provideIsLogin(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal != null) {
            return true;
        } else {
            return false;
        }
    }

    @ModelAttribute("headerNickname")
    public String provideNickname(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal != null) {
            Long id = principal.getId();
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
            return member.getNickname();
        } else {
            return null;
        }
    }

    @ModelAttribute("headerImageUrl")
    public String provideImagerUrl(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal != null) {
            Long id = principal.getId();
            MemberProfile memberProfile = memberProfileRepository.findByMember_Id(id);
            return memberProfile.getImageUrl();
        } else {
            return null;
        }
    }

}
