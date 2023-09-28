package com.project.web.controller.page.general.my;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.controller.dto.my.MyEditPageResponseDto;
import com.project.web.exception.Error404Exception;
import com.project.web.service.MyService;
import com.project.web.service.page.general.my.MyEditPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyEditPageController {
    public final MyEditPageService myEditPageService;

    @GetMapping("/my/edit/{nickname}")
    public String viewMyPageEdit(@PathVariable String nickname,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 Model model) {
        if (!principal.getNickname().equals(nickname)) {
            throw new Error404Exception("허용되지 않은 접근입니다.");
        }
        MyEditPageResponseDto myEditPageResponseDto = myEditPageService.getMyEditPageInfo(nickname);
        model.addAttribute("myEdit", myEditPageResponseDto);
        return "MyEditPage";
    }
}
