package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.my.dto.MyEditFormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyRestController {
    @PostMapping("/private/edit-member-profile/{prevNickname}")
    public String editMemberProfile(@PathVariable(name = "prevNickname") String prevNickname,
                                    MyEditFormRequestDto requestDto,
                                    @AuthenticationPrincipal PrincipalDetails principal,
                                    Model model) throws IOException {
        myService.editMemberProfile(prevNickname, requestDto, principal);
        String encodedNickname = URLEncoder.encode(requestDto.getNickname(), "UTF-8");

        return "redirect:/my/" + encodedNickname;
    }

}
