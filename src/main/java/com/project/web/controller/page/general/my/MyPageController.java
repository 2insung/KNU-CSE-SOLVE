package com.project.web.controller.page.general.my;

import com.project.web.controller.dto.my.MyPageResponseDto;
import com.project.web.service.MyService;
import com.project.web.service.page.general.my.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/my/{nickname}")
    public String viewMyPage(@PathVariable String nickname,
                             Model model) {
        MyPageResponseDto myPageResponseDto = myPageService.getMyPageInfo(nickname);
        model.addAttribute("my", myPageResponseDto);
        return "MyPage";
    }
}
