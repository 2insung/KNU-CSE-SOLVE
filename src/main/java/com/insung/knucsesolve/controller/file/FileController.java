package com.insung.knucsesolve.controller.file;

import com.insung.knucsesolve.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/image/upload")
    public ModelAndView imageUpload(MultipartHttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        MultipartFile uploadFile = request.getFile("upload");

        // '/write' 에서 ckeditor에 이미지를 업로드하면 이미지의 url을 제공해줌.
        String url = fileService.uploadImage(uploadFile);
        modelAndView.addObject("uploaded", true);
        modelAndView.addObject("url", url);

        return modelAndView;
    }
}
