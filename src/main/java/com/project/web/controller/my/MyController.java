package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.auth.dto.UserDto;
import com.project.web.controller.community.Constants;
import com.project.web.controller.community.dto.post.PostPageNumberDto;
import com.project.web.controller.my.dto.*;
import com.project.web.service.auth.UserService;
import com.project.web.service.my.MyService;
import com.project.web.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MyController {
    private final MyService myService;
    private final UserService userService;

    @GetMapping("/my/{nickname}")
    public String viewMyPage(@PathVariable(name = "nickname") String nickname,
                             @AuthenticationPrincipal PrincipalDetails principal,
                             Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        MyDto myDto = myService.getMy(nickname);
        model.addAttribute("my", myDto);
        model.addAttribute("isMy", nickname.equals(userDto.getNickname()));

        return "MyPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my/edit/{nickname}")
    public String viewMyPageEdit(@PathVariable String nickname,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        MyEditDto myEditDto = myService.getMyEdit(nickname, userDto.getNickname());
        model.addAttribute("myEdit", myEditDto);
        model.addAttribute("isMy", nickname.equals(userDto.getNickname()));

        return "MyEditPage";
    }

    @GetMapping("/my/post/{nickname}")
    public String viewMyPagePost(@PathVariable String nickname,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestParam(name = "page", defaultValue = "1") Integer page,
                                 Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        List<MyPostDto> myPostDtoList = myService.getMyPostList(nickname, page);
        model.addAttribute("myPostList", myPostDtoList);
        model.addAttribute("isMy", nickname.equals(userDto.getNickname()));

        Integer myPostCount = myService.getMyPostCount(nickname);
        Integer processedPageNumber = PageUtil.processPageNumber(myPostCount, 20, page);
        if (!page.equals(processedPageNumber)) {
            return "redirect:/my/post/" + nickname + "?page=" + processedPageNumber;
        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> myPostPageNumberList = PageUtil.makePageNumberList(myPostCount, 20, page);
        List<MyPostPageNumberDto> myPostPageNumberDtoList = myPostPageNumberList.stream().map(
                (number) -> MyPostPageNumberDto.builder()
                        .nickname(nickname)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("myPostPageNumberList", myPostPageNumberDtoList);
        model.addAttribute("myPostPageNumber", page);

        return "MyPostPage";
    }

    @GetMapping("/my/comment/{nickname}")
    public String viewMyPageComment(@PathVariable String nickname,
                                    @AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                                    Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        List<MyCommentDto> myCommentDtoList = myService.getMyCommentList(nickname, page);
        model.addAttribute("myCommentList", myCommentDtoList);
        model.addAttribute("isMy", nickname.equals(userDto.getNickname()));

        Integer myCommentCount = myService.getMyCommentCount(nickname);
        Integer processedPageNumber = PageUtil.processPageNumber(myCommentCount, 20, page);
        if (!page.equals(processedPageNumber)) {
            return "redirect:/my/comment/" + nickname + "?page=" + processedPageNumber;
        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> myCommentPageNumberList = PageUtil.makePageNumberList(myCommentCount, 20, page);
        List<MyCommentPageDto> myCommentPageNumberDtoList = myCommentPageNumberList.stream().map(
                (number) -> MyCommentPageDto.builder()
                        .nickname(nickname)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("myCommentPageNumberList", myCommentPageNumberDtoList);
        model.addAttribute("myCommentPageNumber", page);

        return "MyCommentPage";
    }

    @PreAuthorize("isAuthenticated() and #nickname == authentication.principal.nickname")
    @GetMapping("/my/pwEdit/{nickname}")
    public String viewMyPagePwEdit(@PathVariable String nickname,
                                   @AuthenticationPrincipal PrincipalDetails principal,
                                   Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);
        model.addAttribute("isMy", nickname.equals(userDto.getNickname()));

        return "MyPwEditPage";
    }

    @PreAuthorize("isAuthenticated() and #nickname == authentication.principal.nickname")
    @GetMapping("/my/withdraw/{nickname}")
    public String viewMyPageWithdraw(@PathVariable String nickname,
                                     @AuthenticationPrincipal PrincipalDetails principal,
                                     Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);
        model.addAttribute("isMy", nickname.equals(userDto.getNickname()));

        return "MyWithdrawPage";
    }


}
