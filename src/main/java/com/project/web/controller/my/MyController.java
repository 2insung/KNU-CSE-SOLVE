package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.user.UserInfo;
import com.project.web.controller.user.dto.UserDto;
import com.project.web.controller.my.dto.MyEditFormRequestDto;
import com.project.web.controller.my.dto.MyEditPageResponseDto;
import com.project.web.controller.my.dto.MyPageResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;

//@Controller
//@RequiredArgsConstructor
//public class MyController {
//    private final MyService myService;
//
//    @GetMapping("/my/{nickname}")
//    public String viewMyPage(@PathVariable String nickname,
//                             @AuthenticationPrincipal PrincipalDetails principal,
//                             Model model) {
//        UserDto userDto = UserInfo.getUserInfo(principal);
//        model.addAttribute("userHeader", userDto);
//
//        MyPageResponseDto myPageResponseDto = myService.getMyInfo(nickname);
//        model.addAttribute("my", myPageResponseDto);
//
//        return "MyPage";
//    }
//
//    @PreAuthorize("isAuthenticated() and #nickname == authentication.principal.nickname")
//    @GetMapping("/my/edit/{nickname}")
//    public String viewMyPageEdit(@PathVariable String nickname,
//                                 @AuthenticationPrincipal PrincipalDetails principal,
//                                 Model model) {
//        UserDto userDto = UserInfo.getUserInfo(principal);
//        model.addAttribute("userHeader", userDto);
//
//        MyEditPageResponseDto myEditPageResponseDto = myService.getMyEditInfo(nickname);
//        model.addAttribute("myEdit", myEditPageResponseDto);
//
//        return "MyEditPage";
//    }
//
//    @GetMapping("/my/post/{nickname}")
//    public String viewMyPagePost(@PathVariable String nickname,
//                                 @AuthenticationPrincipal PrincipalDetails principal,
//                                 Model model) {
//        UserDto userDto = UserInfo.getUserInfo(principal);
//        model.addAttribute("userHeader", userDto);
//
//        return "MyPostPage";
//    }
//
//    @GetMapping("/my/comment/{nickname}")
//    public String viewMyPageComment(@PathVariable String nickname,
//                                    @AuthenticationPrincipal PrincipalDetails principal,
//                                    Model model) {
//        UserDto userDto = UserInfo.getUserInfo(principal);
//        model.addAttribute("userHeader", userDto);
//
//        return "MyCommentPage";
//    }
//
//    @PreAuthorize("isAuthenticated() and #nickname == authentication.principal.nickname")
//    @GetMapping("/my/pwEdit/{nickname}")
//    public String viewMyPagePwEdit(@PathVariable String nickname,
//                                   @AuthenticationPrincipal PrincipalDetails principal,
//                                   Model model) {
//        UserDto userDto = UserInfo.getUserInfo(principal);
//        model.addAttribute("userHeader", userDto);
//
//        return "MyPwEditPage";
//    }
//
//    @PreAuthorize("isAuthenticated() and #nickname == authentication.principal.nickname")
//    @GetMapping("/my/withdraw/{nickname}")
//    public String viewMyPageWithdraw(@PathVariable String nickname,
//                                     @AuthenticationPrincipal PrincipalDetails principal,
//                                     Model model) {
//        UserDto userDto = UserInfo.getUserInfo(principal);
//        model.addAttribute("userHeader", userDto);
//
//        return "MyWithdrawPage";
//    }
//
//
//    @PostMapping("/private/edit-member-profile/{prevNickname}")
//    public String editMemberProfile(@PathVariable(name = "prevNickname") String prevNickname,
//                                    MyEditFormRequestDto requestDto,
//                                    @AuthenticationPrincipal PrincipalDetails principal,
//                                    Model model) throws IOException {
//        myService.editMemberProfile(prevNickname, requestDto, principal);
//        String encodedNickname = URLEncoder.encode(requestDto.getNickname(), "UTF-8");
//
//        return "redirect:/my/" + encodedNickname;
//    }
//
//    @PostMapping("/api/private/check-valid-nickname")
//    public ResponseEntity<Boolean> checkValidNickname(@RequestParam String nickname) {
//        return ResponseEntity.ok(myService.checkValidNickname(nickname));
//    }
//}
