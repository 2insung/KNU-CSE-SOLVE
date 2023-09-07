package com.project.web.controller.page;

import com.project.web.exception.Error404Exception;
import com.project.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;

    @GetMapping("/my-page/{nickname}")
    public ModelAndView viewMyPage(@PathVariable String nickname){
        ModelAndView modelAndView = new ModelAndView();
        if(!memberService.checkExistingMemberByNickname(nickname)) {
            throw new Error404Exception("유저를 찾을 수 없습니다.");
        }
        modelAndView.setViewName("MyPage");
        return modelAndView;
    }
}
