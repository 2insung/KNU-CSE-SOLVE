package com.project.web.controller.page;

import com.project.web.domain.PostTypeDetail;
import com.project.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BoardPageController {
    private final BoardService boardService;
    @GetMapping("/board")
    public ModelAndView board(@RequestParam(name = "type") String type,
                              @RequestParam(name = "page") String page){
        ModelAndView modelAndView = new ModelAndView();
        PostTypeDetail postTypeDetail = boardService.getPostTypeDetail(type);
        modelAndView.addObject("type", type);
        modelAndView.addObject("alias", postTypeDetail.getAlias());
        modelAndView.addObject("description", postTypeDetail.getDescription());
        modelAndView.setViewName("BoardPage");
        return modelAndView;
    }
}
