package com.project.web.controller;

import com.project.web.controller.dto.my.MyEditFormRequestDto;
import com.project.web.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class MyController {
    private final MyService myService;

    @PostMapping("/private/editMemberProfile/{prevNickname}")
    public String editMemberProfile(@PathVariable(name = "prevNickname") String prevNickname,
                                    MyEditFormRequestDto requestDto,
                                    Model model) throws IOException {
        myService.editMemberProfile(prevNickname, requestDto);
        String encodedNickname = URLEncoder.encode(requestDto.getNickname(), "UTF-8");
        return "redirect:/my/" + encodedNickname;
    }

    @PostMapping("/private/checkValidNickname")
    public ResponseEntity<Boolean> checkValidNickname(@RequestParam String nickname){
        return ResponseEntity.ok(myService.checkValidNickname(nickname));
    }
}
