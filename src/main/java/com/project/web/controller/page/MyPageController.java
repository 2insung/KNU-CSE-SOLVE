package com.project.web.controller.page;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import com.project.web.domain.MemberLevel;
import com.project.web.domain.MemberProfile;
import com.project.web.exception.Error403Exception;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.MemberAuthRepository;
import com.project.web.repository.MemberLevelRepository;
import com.project.web.repository.MemberProfileRepository;
import com.project.web.repository.MemberRepository;
import com.project.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberLevelRepository memberLevelRepository;
    private final MemberAuthRepository memberAuthRepository;

    @GetMapping("/my-page/{nickname}")
    public ModelAndView viewMyPage(@PathVariable String nickname,
                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ModelAndView modelAndView = new ModelAndView();
        if (!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByNickname(nickname);
        MemberProfile memberProfile = memberProfileRepository.findByMember_Nickname(nickname);
        MemberAuth memberAuth = memberAuthRepository.findByMember_Nickname(nickname);
        MemberLevel memberLevel = memberLevelRepository.findByMember_Nickname(nickname);
        if (principalDetails != null) {
            if (principalDetails.getId().equals(member.getId())) {
                modelAndView.addObject("isMyPage", true);
            } else {
                modelAndView.addObject("isMyPage", false);
            }
        }
        modelAndView.addObject("profileImageUrl", memberProfile.getImageUrl());
        modelAndView.addObject("profileNickname", nickname);
        modelAndView.addObject("profileRank", memberLevel.getLevel().getName());
        modelAndView.addObject("profileEmail", memberAuth.getUsername());
        modelAndView.addObject("profileCreateAt", memberProfile.getCreatedAt());
        modelAndView.addObject("profileDescription", memberProfile.getDescription());
        modelAndView.addObject("nickname", nickname);
        modelAndView.setViewName("MyPage");
        return modelAndView;

    }

    @GetMapping("/my-page/{nickname}/edit")
    public ModelAndView viewMyPageEdit(@PathVariable String nickname,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ModelAndView modelAndView = new ModelAndView();
        if (!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByNickname(nickname);
        MemberProfile memberProfile = memberProfileRepository.findByMember_Nickname(nickname);
        if (principalDetails != null) {
            if (principalDetails.getId().equals(member.getId())) {
                modelAndView.addObject("isMyPage", true);
            } else {
                throw new Error403Exception("허용되지 않은 접근입니다.");
            }
        } else {
            throw new Error403Exception("허용되지 않은 접근입니다.");
        }
        modelAndView.addObject("id", member.getId());
        modelAndView.addObject("nickname", nickname);
        modelAndView.addObject("imageUrl", memberProfile.getImageUrl());
        modelAndView.addObject("description", memberProfile.getDescription());
        modelAndView.setViewName("MyPage-Edit");
        return modelAndView;
    }

    @GetMapping("/my-page/{nickname}/post")
    public ModelAndView viewMyPagePost(@PathVariable String nickname,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ModelAndView modelAndView = new ModelAndView();
        if (!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByNickname(nickname);
        if (principalDetails != null) {
            if (principalDetails.getId().equals(member.getId())) {
                modelAndView.addObject("isMyPage", true);
            } else {
                modelAndView.addObject("isMyPage", false);
            }
        }
        modelAndView.addObject("nickname", nickname);
        modelAndView.setViewName("MyPage-Post");
        return modelAndView;
    }

    @GetMapping("/my-page/{nickname}/comment")
    public ModelAndView viewMyPageComment(@PathVariable String nickname,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ModelAndView modelAndView = new ModelAndView();
        if (!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByNickname(nickname);
        if (principalDetails != null) {
            if (principalDetails.getId().equals(member.getId())) {
                modelAndView.addObject("isMyPage", true);
            } else {
                modelAndView.addObject("isMyPage", false);
            }
        }
        modelAndView.addObject("nickname", nickname);
        modelAndView.setViewName("MyPage-Comment");
        return modelAndView;
    }

    @GetMapping("/my-page/{nickname}/pwEdit")
    public ModelAndView viewMyPagePwEdit(@PathVariable String nickname,
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ModelAndView modelAndView = new ModelAndView();
        if (!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByNickname(nickname);
        if (principalDetails != null) {
            if (principalDetails.getId().equals(member.getId())) {
                modelAndView.addObject("isMyPage", true);
            } else {
                throw new Error403Exception("허용되지 않은 접근입니다.");
            }
        } else {
            throw new Error403Exception("허용되지 않은 접근입니다.");
        }
        modelAndView.addObject("nickname", nickname);
        modelAndView.setViewName("MyPage-PwEdit");
        return modelAndView;
    }

    @GetMapping("/my-page/{nickname}/withdraw")
    public ModelAndView viewMyPageWithdraw(@PathVariable String nickname,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ModelAndView modelAndView = new ModelAndView();
        if (!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        Member member = memberRepository.findByNickname(nickname);
        if (principalDetails != null) {
            if (principalDetails.getId().equals(member.getId())) {
                modelAndView.addObject("isMyPage", true);
            } else {
                throw new Error403Exception("허용되지 않은 접근입니다.");
            }
        } else {
            throw new Error403Exception("허용되지 않은 접근입니다.");
        }
        modelAndView.addObject("nickname", nickname);
        modelAndView.setViewName("MyPage-Withdraw");
        return modelAndView;
    }
}
