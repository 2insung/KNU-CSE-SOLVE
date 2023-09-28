package com.project.web.controller;

import com.project.web.controller.dto.post.CommentRequestDto;
import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/private/saveComment")
    public String saveComment(@ModelAttribute CommentRequestDto commentRequestDto,
                              @RequestParam(name = "postId") String postId,
                              @RequestParam(name = "type") String type,
                              @RequestParam(name = "commentId", defaultValue = "-1") String commentId,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.saveComment(Long.parseLong(postId), commentRequestDto, Long.parseLong(commentId), principalDetails);
        String url = "redirect:" + "/post?type=" + type + "&id=" + postId;
        return url;
    }
}
