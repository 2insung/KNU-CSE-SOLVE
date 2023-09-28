package com.project.web.controller.page.general.my;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.Member;
import com.project.web.exception.Error403Exception;
import com.project.web.exception.Error404Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MyWithdrawPageController {
    @GetMapping("/my/withdraw/{nickname}")
    public String viewMyPageWithdraw(@PathVariable String nickname,
                                     @AuthenticationPrincipal PrincipalDetails principal,
                                     Model model) {
        if (!principal.getNickname().equals(nickname)) {
            throw new Error404Exception("허용되지 않은 접근입니다.");
        }

        return "MyWithdrawPage";
    }
}
