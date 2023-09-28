package com.project.web.controller.page.general.board;

import com.project.web.controller.dto.post.PostPreviewResponseDto;
import com.project.web.service.BoardService;
import com.project.web.service.page.general.board.BoardPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardPageController {
    private final BoardPageService boardPageService;
    @GetMapping("/board")
    public String board(@RequestParam(name = "type") String type,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "searchType", defaultValue = "") String searchType,
                        @RequestParam(name = "title", defaultValue = "") String title,
                        @RequestParam(name = "content", defaultValue = "") String content,
                        @RequestParam(name = "author", defaultValue = "") String author,
                        @RequestParam(name = "comment", defaultValue = "") String comment,
                        Model model){

        if(searchType.equals("")){
            List<PostPreviewResponseDto> postPreviewResponseDtoList =  boardPageService.getPostPreviewPageDefault(type, page);
            model.addAttribute("postPreviewList", postPreviewResponseDtoList);
        }
        else if(searchType.equals("title")){

        }
        else if (searchType.equals("content")) {

        }
        else if(searchType.equals("title_content")){

        }
        else if(searchType.equals("author")){

        }
        else if(searchType.equals("comment")){

        }

        return "BoardPage";
    }
}
