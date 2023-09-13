package com.project.web.controller.page;

import com.project.web.controller.dto.PostPreviewResponseDto;
import com.project.web.domain.PostTypeDetail;
import com.project.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardPageController {
    private final BoardService boardService;
    private final Integer size = 15;
    @GetMapping("/board")
    public ModelAndView board(@RequestParam(name = "type") String type,
                              @RequestParam(name = "page", defaultValue = "1") String page,
                              @RequestParam(name = "searchType", defaultValue = "") String searchType,
                              @RequestParam(name = "title", defaultValue = "") String title,
                              @RequestParam(name = "content", defaultValue = "") String content,
                              @RequestParam(name = "author", defaultValue = "") String author,
                              @RequestParam(name = "comment", defaultValue = "") String comment){
        ModelAndView modelAndView = new ModelAndView();
        PostTypeDetail postTypeDetail = boardService.getPostTypeDetail(type);
        modelAndView.addObject("type", type);
        modelAndView.addObject("alias", postTypeDetail.getAlias());
        modelAndView.addObject("description", postTypeDetail.getDescription());
        modelAndView.setViewName("BoardPage");

        if(searchType.equals("")){
            List<PostPreviewResponseDto> postPreviewResponseDtoList =  boardService.getPostPreviewPageDefault(Integer.parseInt(page), size);
            modelAndView.addObject("postPreviewResponseDtoList", postPreviewResponseDtoList);
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

        return modelAndView;
    }
}
