package com.project.web.controller;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.controller.dto.post.SavePostRequestDto;
import com.project.web.exception.Error403Exception;
import com.project.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/private/savePost")
    public ModelAndView savePost(@RequestParam String type,
                                 SavePostRequestDto savePostRequestDto,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        ModelAndView modelAndView = new ModelAndView();
        if(principalDetails != null){
            postService.savePost(type, savePostRequestDto, principalDetails);
            modelAndView.setViewName("redirect:/board?type=" + type + "&page=1");
        }
        else{
            throw new Error403Exception("허용되지 않은 접근입니다.");
        }
        return modelAndView;
    }
}
