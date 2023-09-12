package com.project.web.controller;

import com.project.web.controller.dto.my.EditMemberProfileRequestDto;
import com.project.web.service.MyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class MyController {
    private final MyService myService;

    @PostMapping("/public/editMemberProfile")
    public ModelAndView editMemberProfile(EditMemberProfileRequestDto requestDto) throws IOException {
        myService.editMemberProfile(requestDto);
        ModelAndView modelAndView = new ModelAndView();
        String encodedNickname = URLEncoder.encode(requestDto.getNickname(), "UTF-8");
        modelAndView.setViewName("redirect:/my-page/" + encodedNickname);
        return modelAndView;
    }

    @PostMapping("/private/checkValidNickname")
    public ResponseEntity<Boolean> checkValidNickname(@RequestParam String nickname){
        return ResponseEntity.ok(myService.checkValidNickname(nickname));
    }
}
