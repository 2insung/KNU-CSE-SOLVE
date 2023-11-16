package com.project.web.controller.my;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.my.dto.*;
import com.project.web.service.board.CommentService;
import com.project.web.service.board.PostService;
import com.project.web.service.my.MyService;
import com.project.web.service.upload.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyRestController {
    private final MyService myService;
    private final PostService postService;
    private final CommentService commentService;
    private final ImageUploadService imageUploadService;

    @PreAuthorize("isAuthenticated() and ((#updateMyRequestDto.userId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PostMapping("/api/update-my")
    public ResponseEntity<UpdateMyResponseDto> updateMy(@ModelAttribute UpdateMyRequestDto updateMyRequestDto,
                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        Integer userId = updateMyRequestDto.getUserId();
        String nickname = updateMyRequestDto.getNickname();
        String imageURL = imageUploadService.uploadImage(updateMyRequestDto.getFile());
        String description = updateMyRequestDto.getDescription();
        String grade = updateMyRequestDto.getGrade();
        String admissionYear = updateMyRequestDto.getAdmissionYear();
        String department = updateMyRequestDto.getDepartment();

        myService.updateMy(userId, nickname, imageURL, grade, description, admissionYear, department);


        return ResponseEntity.ok(
                UpdateMyResponseDto.builder()
                        .userId(userId)
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
        Integer currentPage = deleteMyPostRequestDto.getCurrentPage();

        postService.deletePost(boardId, postId);

        return ResponseEntity.ok(
                DeleteMyPostResponseDto.builder()
                        .userId(postAuthorId)
                        .currentPage(currentPage)
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
        Integer currentPage = deleteMyCommentRequestDto.getCurrentPage();

        commentService.deleteComment(postId, commentId);

        return ResponseEntity.ok(
                DeleteMyCommentResponseDto.builder()
                        .userId(commentAuthorId)
                        .currentPage(currentPage)
                        .build()
        );
    }


}
