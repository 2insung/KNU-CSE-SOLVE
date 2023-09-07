package com.project.web.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootPageController {
    @GetMapping("/")
    public ModelAndView viewRootPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("RootPage");
        return modelAndView;
    }



}
