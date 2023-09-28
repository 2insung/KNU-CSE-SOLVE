package com.project.web.controller.page.general;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootPageController {
    @GetMapping("/")
    public String viewRootPage(Model model){
        return "RootPage";
    }
}
