package com.project.web.controller.page.general.my.controllerAdvice;

import com.project.web.controller.dto.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@ControllerAdvice("com.project.web.controller.page.general.my")
public class MyGlobalControllerAdvice {
    @ModelAttribute
    public void myGlobal(@PathVariable(name="nickname") String nickname, @AuthenticationPrincipal PrincipalDetails principal, Model model) {
        if(principal != null && principal.getNickname().equals(nickname)){
            model.addAttribute("isMy", true);
        }
        else{
            model.addAttribute("isMy", false);
        }
    }
}
