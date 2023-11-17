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

    @GetMapping("/my/{userId}")
    public String viewMyPage(@PathVariable(name = "userId") Integer userId,
                             @AuthenticationPrincipal PrincipalDetails principal,
                             Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        MyDto myDto = myService.getMy(userId);
        model.addAttribute("my", myDto);
        model.addAttribute("isMy", userId.equals(userDto.getUserId()));

        return "MyPage";
    }

    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
    @GetMapping("/my/edit/{userId}")
    public String viewMyPageEdit(@PathVariable(name = "userId") Integer userId,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        MyEditDto myEditDto = myService.getMyEdit(userId);
        model.addAttribute("myEdit", myEditDto);
        model.addAttribute("isMy", userId.equals(userDto.getUserId()));

        return "MyEditPage";
    }

    @GetMapping("/my/post/{userId}")
    public String viewMyPagePost(@PathVariable(name = "userId") Integer userId,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestParam(name = "page", defaultValue = "1") Integer page,
                                 Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        model.addAttribute("isMy", userId.equals(userDto.getUserId()));

        Integer myPostCount = myService.getMyPostCount(userId);
        Integer processedPageNumber = PageUtil.processPageNumber(myPostCount, 3, page);
        if (!page.equals(processedPageNumber)) {
            return "redirect:/my/post/" + userId + "?page=" + processedPageNumber;
        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> myPostPageNumberList = PageUtil.makePageNumberList(myPostCount, 3, page);
        List<MyPostPageNumberDto> myPostPageNumberDtoList = myPostPageNumberList.stream().map(
                (number) -> MyPostPageNumberDto.builder()
                        .userId(userId)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());

        List<MyPostDto> myPostDtoList = myService.getMyPostList(userId, page);
        model.addAttribute("myPostList", myPostDtoList);
        model.addAttribute("myPostPageNumberList", myPostPageNumberDtoList);
        model.addAttribute("myPostPageNumber", page);

        return "MyPostPage";
    }

    @GetMapping("/my/comment/{userId}")
    public String viewMyPageComment(@PathVariable(name = "userId") Integer userId,
                                    @AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                                    Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        model.addAttribute("isMy", userId.equals(userDto.getUserId()));

        Integer myCommentCount = myService.getMyCommentCount(userId);
        Integer processedPageNumber = PageUtil.processPageNumber(myCommentCount, 3, page);
        if (!page.equals(processedPageNumber)) {
            return "redirect:/my/comment/" + userId + "?page=" + processedPageNumber;
        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> myCommentPageNumberList = PageUtil.makePageNumberList(myCommentCount, 3, page);
        List<MyCommentPageDto> myCommentPageNumberDtoList = myCommentPageNumberList.stream().map(
                (number) -> MyCommentPageDto.builder()
                        .userId(userId)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());

        List<MyCommentDto> myCommentDtoList = myService.getMyCommentList(userId, page);
        model.addAttribute("myCommentList", myCommentDtoList);
        model.addAttribute("myCommentPageNumberList", myCommentPageNumberDtoList);
        model.addAttribute("myCommentPageNumber", page);

        return "MyCommentPage";
    }

    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
    @GetMapping("/my/pwEdit/{userId}")
    public String viewMyPagePwEdit(@PathVariable(name = "userId") Integer userId,
                                   @AuthenticationPrincipal PrincipalDetails principal,
                                   Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);
        model.addAttribute("isMy", userId.equals(userDto.getUserId()));

        Integer myId = myService.getMyId(userId);
        model.addAttribute("myId", myId);

        return "MyPwEditPage";
    }

    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.userId")
    @GetMapping("/my/withdraw/{userId}")
    public String viewMyPageWithdraw(@PathVariable(name = "userId") Integer userId,
                                     @AuthenticationPrincipal PrincipalDetails principal,
                                     Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);
        model.addAttribute("isMy", userId.equals(userDto.getUserId()));

        Integer myId = myService.getMyId(userId);
        model.addAttribute("myId", myId);

        return "MyWithdrawPage";
    }


}
