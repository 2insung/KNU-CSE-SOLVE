package com.project.web.controller.page;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.controller.page.RootPageController;
import com.project.web.controller.page.SignUpPageController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes =
        {RootPageController.class,
                SignUpPageController.class,
                MyPageController.class})
public class HeaderLayoutControllerAdvice {

    @ModelAttribute("isLogin")
    public Boolean provideLoginStatus(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal != null) {
            return true;
        } else {
            return false;
        }
    }

    @ModelAttribute("nickname")
    public String provideUserNickname(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal != null) {
            return principal.getNickname();
        } else {
            return null;
        }
    }

}
