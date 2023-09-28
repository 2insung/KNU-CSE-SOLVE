package com.project.web.controller.page.general.my;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyCommentPageController {
    @GetMapping("/my/comment/{nickname}")
    public String viewMyPageComment(@PathVariable String nickname,
                                    Model model) {

        return "MyCommentPage";
    }
}
