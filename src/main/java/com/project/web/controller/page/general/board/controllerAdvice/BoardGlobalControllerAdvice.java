package com.project.web.controller.page.general.board.controllerAdvice;

import com.project.web.domain.BoardDetail;
import com.project.web.service.page.general.board.BoardGlobalService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@ControllerAdvice("com.project.web.controller.page.general.board")
@RequiredArgsConstructor
public class BoardGlobalControllerAdvice {
    private final BoardGlobalService boardGlobalService;

    @ModelAttribute
    public void boardGlobal(@RequestParam(name = "type") String type, Model model) {
        BoardDetail boardDetail = boardGlobalService.getBoardDetail(type);
        model.addAttribute("boardType", boardDetail.getBoard().getType());
        model.addAttribute("boardAlias", boardDetail.getAlias());
        model.addAttribute("boardDescription", boardDetail.getDescription());
    }
}
