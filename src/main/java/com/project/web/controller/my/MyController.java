package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.auth.dto.UserDto;
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

// My 관련 뷰 반환
@Controller
@RequiredArgsConstructor
public class MyController {
    private final MyService myService;
    private final UserService userService;

    @GetMapping("/my/{memberId}")
    public String my(@PathVariable(name = "memberId") Integer memberId,
                     @AuthenticationPrincipal PrincipalDetails principal,
                     Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 사용자 정보를 제공.
        MyDto myDto = myService.getMyDto(memberId);
        model.addAttribute("my", myDto);

        // 현재 보고있는 사용자가 나와 일치하는지.
        model.addAttribute("isMy", memberId.equals(userDto.getUserId()));

        return "my/MyPage";
    }

    @PreAuthorize("isAuthenticated() and #memberId == authentication.principal.userId")
    @GetMapping("/my/edit/{memberId}")
    public String myEdit(@PathVariable(name = "memberId") Integer memberId,
                         @AuthenticationPrincipal PrincipalDetails principal,
                         Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 이전 사용자 정보를 제공.
        MyEditDto myEditDto = myService.getMyEditDto(memberId);
        model.addAttribute("myEdit", myEditDto);

        // 현재 보고있는 사용자가 나와 일치하는지.
        model.addAttribute("isMy", memberId.equals(userDto.getUserId()));

        return "my/MyEditPage";
    }

    @GetMapping("/my/post/{memberId}")
    public String myPost(@PathVariable(name = "memberId") Integer memberId,
                         @AuthenticationPrincipal PrincipalDetails principal,
                         @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                         Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 사용자의 게시글 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        Integer myPostCount = myService.getMyPostsCount(memberId);
        Integer processedPageNumber = PageUtil.processPageNumber(myPostCount, MyConstants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/my/post/" + memberId + "?page=" + processedPageNumber;
        }

        Integer userId = principal != null ? principal.getUserId() : null;
        List<MyPostDto> myPostDtoList = myService.getMyPostDtos(userId, memberId, MyConstants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("myPostList", myPostDtoList);

        // 사용자 게시글 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트)
        List<Integer> myPostPageNumberList = PageUtil.makePageNumberList(myPostCount, MyConstants.POST_PAGE_SIZE, pageNumber);
        List<MyPostPageNumberDto> myPostPageNumberDtoList =
                myPostPageNumberList.stream().map(
                        (number) -> MyPostPageNumberDto.builder()
                                .memberId(memberId)
                                .pageNumber(number)
                                .build()
                ).collect(Collectors.toList());
        model.addAttribute("myPostPageNumber", pageNumber);
        model.addAttribute("myPostPageNumberList", myPostPageNumberDtoList);

        // 현재 보고있는 사용자가 나와 일치하는지.
        model.addAttribute("isMy", memberId.equals(userDto.getUserId()));

        return "my/MyPostPage";
    }

    @GetMapping("/my/comment/{memberId}")
    public String myComment(@PathVariable(name = "memberId") Integer memberId,
                            @AuthenticationPrincipal PrincipalDetails principal,
                            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                            Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 사용자의 댓글 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        Integer myCommentCount = myService.getMyCommentsCount(memberId);
        Integer processedPageNumber = PageUtil.processPageNumber(myCommentCount, MyConstants.COMMENT_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/my/comment/" + memberId + "?page=" + processedPageNumber;
        }

        Integer userId = principal != null ? principal.getUserId() : null;
        List<MyCommentDto> myCommentDtoList = myService.getMyCommentDtos(userId, memberId, MyConstants.COMMENT_PAGE_SIZE, pageNumber);
        model.addAttribute("myCommentList", myCommentDtoList);

        // 사용자 댓글 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트)
        List<Integer> myCommentPageNumberList = PageUtil.makePageNumberList(myCommentCount, MyConstants.COMMENT_PAGE_SIZE, pageNumber);
        List<MyCommentPageNumberDto> myCommentPageNumberDtoList = myCommentPageNumberList.stream().map(
                (number) -> MyCommentPageNumberDto.builder()
                        .memberId(memberId)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("myCommentPageNumber", pageNumber);
        model.addAttribute("myCommentPageNumberList", myCommentPageNumberDtoList);

        // 현재 보고있는 사용자가 나와 일치하는지.
        model.addAttribute("isMy", memberId.equals(userDto.getUserId()));

        return "my/MyCommentPage";
    }

    @PreAuthorize("isAuthenticated() and #memberId == authentication.principal.userId")
    @GetMapping("/my/pwEdit/{memberId}")
    public String myPwEdit(@PathVariable(name = "memberId") Integer memberId,
                           @AuthenticationPrincipal PrincipalDetails principal,
                           Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 현재 보고있는 사용자가 나와 일치하는지.
        model.addAttribute("isMy", memberId.equals(userDto.getUserId()));

        return "my/MyPwEditPage";
    }

    @PreAuthorize("isAuthenticated() and #memberId == authentication.principal.userId")
    @GetMapping("/my/withdraw/{memberId}")
    public String myWithdraw(@PathVariable(name = "memberId") Integer memberId,
                             @AuthenticationPrincipal PrincipalDetails principal,
                             Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 현재 보고있는 사용자가 나와 일치하는지.
        model.addAttribute("isMy", memberId.equals(userDto.getUserId()));

        return "my/MyWithdrawPage";
    }
}
