package com.project.web.controller;

import com.project.web.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final S3UploaderService s3UploaderService;
    @PostMapping("/image/upload")
    public ModelAndView image(MultipartHttpServletRequest request) throws Exception{
        ModelAndView modelAndView = new ModelAndView("jsonView");
        MultipartFile uploadFile = request.getFile("upload");

        String url = s3UploaderService.upload(uploadFile, "tempImages");
        modelAndView.addObject("uploaded", true);
        modelAndView.addObject("url", url);

        return modelAndView;
    }

    @PostMapping("/send")
    public ModelAndView send(@ModelAttribute TestDto testDto){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        Document doc = Jsoup.parse(testDto.getContent());

        // 모든 텍스트를 파싱
        String allText = doc.text();
        String first20Chars = allText.length() > 20 ? allText.substring(0, 20) : allText;

        // 첫 번째 이미지의 URL을 파싱
        Element imgElement = doc.select("img").first();
        String firstImageUrl = imgElement != null ? imgElement.attr("src") : "이미지가 없습니다.";

        System.out.println("처음 20 글자: " + first20Chars);
        System.out.println("첫 번째 이미지 URL: " + firstImageUrl);
        return modelAndView;
    }
}
