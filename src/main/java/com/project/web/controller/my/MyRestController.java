package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.my.dto.rest.*;
import com.project.web.service.board.CommentService;
import com.project.web.service.board.PostService;
import com.project.web.service.my.MyService;
import com.project.web.service.upload.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// My REST API
@RestController
@RequiredArgsConstructor
public class MyRestController {
    private final MyService myService;
    private final PostService postService;
    private final CommentService commentService;
    private final ImageUploadService imageUploadService;

    @PreAuthorize("isAuthenticated() and ((#updateMyRequestDto.memberId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PostMapping("/api/update-my")
    public ResponseEntity<UpdateMyResponseDto> updateMy(@ModelAttribute UpdateMyRequestDto updateMyRequestDto,
                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        Integer memberId = updateMyRequestDto.getMemberId();
        String nickname = updateMyRequestDto.getNickname();
        String imageURL = imageUploadService.uploadImage(updateMyRequestDto.getFile());
        String description = updateMyRequestDto.getDescription();
        String grade = updateMyRequestDto.getGrade();
        String admissionYear = updateMyRequestDto.getAdmissionYear();
        String department = updateMyRequestDto.getDepartment();

        // 사용자 정보 수정.
        myService.updateMy(memberId, nickname, imageURL, grade, description, admissionYear, department);

        // my/{memberId}로 redirect하기 위함.
        return ResponseEntity.ok(
                UpdateMyResponseDto.builder()
                        .memberId(memberId)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#deleteMyPostRequestDto.postAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/api/delete-my-post")
    public ResponseEntity<DeleteMyPostResponseDto> deleteMyPost(@RequestBody DeleteMyPostRequestDto deleteMyPostRequestDto,
                                                                @AuthenticationPrincipal PrincipalDetails principal) {
        Integer boardId = deleteMyPostRequestDto.getBoardId();
        Integer postId = deleteMyPostRequestDto.getPostId();
        Integer postAuthorId = deleteMyPostRequestDto.getPostAuthorId();
        Integer currentPageNumber = deleteMyPostRequestDto.getCurrentPageNumber();

        // 사용자 작성 글 삭제.
        postService.deletePost(boardId, postId);

        // 삭제 후 보여줄 사용자 작성글 페이지의 번호를 계산함.
        Integer totalMyPostCount = myService.getMyPostCount(postAuthorId);
        Integer totalPageNumber = ((totalMyPostCount - 1) / MyConstants.POST_PAGE_SIZE) + 1;
        Integer pageNumber = totalPageNumber < currentPageNumber ? totalPageNumber : currentPageNumber;

        return ResponseEntity.ok(
                DeleteMyPostResponseDto.builder()
                        .memberId(postAuthorId)
                        .pageNumber(pageNumber)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#deleteMyCommentRequestDto.commentAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/api/delete-my-comment")
    public ResponseEntity<DeleteMyCommentResponseDto> deleteMyComment(@RequestBody DeleteMyCommentRequestDto deleteMyCommentRequestDto,
                                                                      @AuthenticationPrincipal PrincipalDetails principal) {
        Integer postId = deleteMyCommentRequestDto.getPostId();
        Integer commentId = deleteMyCommentRequestDto.getCommentId();
        Integer commentAuthorId = deleteMyCommentRequestDto.getCommentAuthorId();
        Integer currentPageNumber = deleteMyCommentRequestDto.getCurrentPageNumber();

        // 사용자 작성 댓글 삭제.
        commentService.deleteComment(postId, commentId);

        // 삭제 후 보여줄 사용자 댓글 페이지의 번호를 계산함.
        Integer totalMyCommentCount = myService.getMyCommentCount(commentAuthorId);
        Integer totalPageNumber = ((totalMyCommentCount - 1) / MyConstants.COMMENT_PAGE_SIZE) + 1;
        Integer pageNumber = totalPageNumber < currentPageNumber ? totalPageNumber : currentPageNumber;

        return ResponseEntity.ok(
                DeleteMyCommentResponseDto.builder()
                        .memberId(commentAuthorId)
                        .pageNumber(pageNumber)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#updatePasswordRequestDto.memberId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/api/update-my-password")
    public ResponseEntity<UpdatePasswordResponseDto> deleteMyComment(@RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
                                                                     @AuthenticationPrincipal PrincipalDetails principal) {
        Integer memberId = updatePasswordRequestDto.getMemberId();
        String currentPassword = updatePasswordRequestDto.getCurrentPassword();
        String changePassword = updatePasswordRequestDto.getChangePassword();

        // 사용자 비밀번호 변경 함수.
        myService.updateMyPassword(memberId, currentPassword, changePassword);

        return ResponseEntity.ok(
                UpdatePasswordResponseDto.builder()
                        .memberId(memberId)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#withdrawRequestDto.memberId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/api/withdraw")
    public ResponseEntity<WithdrawResponseDto> withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto,
                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        Integer memberId = withdrawRequestDto.getMemberId();
        Boolean isSuccess = myService.withdraw(memberId);
        return ResponseEntity.ok(
                WithdrawResponseDto.builder()
                        .isSuccess(isSuccess)
                        .build()
        );
    }
}
