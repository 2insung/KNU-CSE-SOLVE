package com.project.web.controller.page.general;

import com.project.web.controller.dto.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("com.project.web.controller.page.general")
public class HeaderControllerAdvice {
    @ModelAttribute
    public void attributesToHeaderLayout(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        if (principal != null) {
            model.addAttribute("headerIsLogin", true);
            model.addAttribute("headerNickname", principal.getNickname());
            model.addAttribute("headerProfileImage", principal.getProfileImage());
        } else {
            model.addAttribute("headerIsLogin", false);
        }
    }
}
