package com.project.web.controller.page.general.board;

import com.project.web.controller.dto.post.CommentResponseDto;
import com.project.web.controller.dto.post.PostPageResponseDto;
import com.project.web.service.CommentService;
import com.project.web.service.PostService;
import com.project.web.service.page.general.board.PostPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostPageController {
    private final PostPageService postPageService;

    @GetMapping("/post")
    public String viewPostPage(@RequestParam(name = "type") String type,
                               @RequestParam(name = "postId") Long postId,
                               Model model){
        PostPageResponseDto postPageResponseDto = postPageService.getPostWithTypeAndId(type, postId);
        model.addAttribute("post", postPageResponseDto);
        return "PostPage";
    }

    @GetMapping("/comment")
    public String viewComment(@RequestParam(name = "page") Integer page,
                              @RequestParam(name = "postId") Long postId,
                              Model model){
        List<CommentResponseDto> commentResponseDtoList = postPageService.getCommentWithPageableAndPostId(page, postId);
        model.addAttribute("comments", commentResponseDtoList);
        return "PostPage :: #commentPage";
    }


}
