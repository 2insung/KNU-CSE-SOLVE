package com.project.web.controller.page;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.MemberLevel;
import com.project.web.domain.PostTypeDetail;
import com.project.web.exception.Error403Exception;
import com.project.web.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class WritePageController {
    private final WriteService writeService;
    @GetMapping("/write")
    public ModelAndView board(@RequestParam(name = "type") String type,
                              @AuthenticationPrincipal PrincipalDetails principalDetails){
        ModelAndView modelAndView = new ModelAndView();
        if (principalDetails == null){
            throw new Error403Exception("잘못된 접근입니다.");
        }
        if(writeService.hasNotificationAccess(principalDetails.getId())){
            modelAndView.addObject("hasNotificationAccess", true);
        }
        else{
            modelAndView.addObject("hasNotificationAccess", false);
        }
        String alias = writeService.getPostTypeAlias(type);
        modelAndView.addObject("type", type);
        modelAndView.addObject("alias", alias);
        modelAndView.setViewName("WritePage");
        return modelAndView;
    }
}
