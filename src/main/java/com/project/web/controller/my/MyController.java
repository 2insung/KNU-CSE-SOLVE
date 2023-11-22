package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.my.dto.view.*;
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

// My 관련 뷰 반환
@Controller
@RequiredArgsConstructor
public class MyController {
    private final MyService myService;

    @GetMapping("/my/{memberId}")
    public String viewMyPage(@PathVariable(name = "memberId") Integer memberId,
                             @AuthenticationPrincipal PrincipalDetails principal,
                             Model model) {
        // 사용자 정보를 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
        MyDto myDto = myService.getMyDto(userId, memberId);
        model.addAttribute("my", myDto);

        return "my/MyPage";
    }

    @PreAuthorize("isAuthenticated() and #memberId == authentication.principal.userId")
    @GetMapping("/my-edit/{memberId}")
    public String viewMyEditPage(@PathVariable(name = "memberId") Integer memberId,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 Model model) {
        // 이전 사용자 정보를 제공.
        MyEditDto myEditDto = myService.getMyEditDto(memberId);
        model.addAttribute("myEdit", myEditDto);

        return "my/MyEditPage";
    }

    @GetMapping("/my/post/{memberId}")
    public String viewMyPostPage(@PathVariable(name = "memberId") Integer memberId,
                                 @AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                                 Model model) {
        // 사용자의 게시글 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        Integer myPostCount = myService.getMyPostsCount(memberId);
        Integer processedPageNumber = PageUtil.processPageNumber(myPostCount, MyConstants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/my/post/" + memberId + "?page=" + processedPageNumber;
        }

        Integer userId = principal != null ? principal.getUserId() : null;
        List<MyPostDto> myPostDtos = myService.getMyPostDtos(userId, memberId, MyConstants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("myPostList", myPostDtos);

        // 사용자 게시글 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트)
        List<Integer> myPostPageNumberList = PageUtil.makePageNumberList(myPostCount, MyConstants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("myPostPageNumber", pageNumber);
        model.addAttribute("myPostPageNumberList", myPostPageNumberList);

        return "my/MyPostPage";
    }

    @GetMapping("/my/comment/{memberId}")
    public String viewMyCommentPage(@PathVariable(name = "memberId") Integer memberId,
                                    @AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                                    Model model) {
        // 사용자의 댓글 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        Integer myCommentCount = myService.getMyCommentsCount(memberId);
        Integer processedPageNumber = PageUtil.processPageNumber(myCommentCount, MyConstants.COMMENT_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/my/comment/" + memberId + "?page=" + processedPageNumber;
        }

        Integer userId = principal != null ? principal.getUserId() : null;
        List<MyCommentDto> myCommentDtos = myService.getMyCommentDtos(userId, memberId, MyConstants.COMMENT_PAGE_SIZE, pageNumber);
        model.addAttribute("myCommentList", myCommentDtos);

        // 사용자 댓글 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트)
        List<Integer> myCommentPageNumberList = PageUtil.makePageNumberList(myCommentCount, MyConstants.COMMENT_PAGE_SIZE, pageNumber);
        model.addAttribute("myCommentPageNumber", pageNumber);
        model.addAttribute("myCommentPageNumberList", myCommentPageNumberList);

        return "my/MyCommentPage";
    }

    @GetMapping("/my/scrap/{memberId}")
    public String viewMyScrapPage(@PathVariable(name = "memberId") Integer memberId,
                                  @AuthenticationPrincipal PrincipalDetails principal,
                                  @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                                  Model model) {
        // 사용자의 댓글 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        Integer myScrapCount = myService.getMyScrapsCount(memberId);
        Integer processedPageNumber = PageUtil.processPageNumber(myScrapCount, MyConstants.SCRAP_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/my/scrap/" + memberId + "?page=" + processedPageNumber;
        }

        Integer userId = principal != null ? principal.getUserId() : null;
        List<MyScrapDto> myScrapDtos = myService.getMyScrapDtos(userId, memberId, MyConstants.SCRAP_PAGE_SIZE, pageNumber);
        model.addAttribute("myScrapList", myScrapDtos);

        // 사용자 댓글 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트)
        List<Integer> myScrapPageNumberList = PageUtil.makePageNumberList(myScrapCount, MyConstants.SCRAP_PAGE_SIZE, pageNumber);
        model.addAttribute("myScrapPageNumber", pageNumber);
        model.addAttribute("myScrapPageNumberList", myScrapPageNumberList);

        return "my/MyScrapPage";
    }

    @PreAuthorize("isAuthenticated() and #memberId == authentication.principal.userId")
    @GetMapping("/my-edit/pw/{memberId}")
    public String viewMyPwEditPage(@PathVariable(name = "memberId") Integer memberId,
                                   @AuthenticationPrincipal PrincipalDetails principal,
                                   Model model) {
        return "my/MyPwEditPage";
    }

    @PreAuthorize("isAuthenticated() and #memberId == authentication.principal.userId")
    @GetMapping("/my-edit/withdraw/{memberId}")
    public String viewMyWithdrawPage(@PathVariable(name = "memberId") Integer memberId,
                                     @AuthenticationPrincipal PrincipalDetails principal,
                                     Model model) {
        return "my/MyWithdrawPage";
    }
}
