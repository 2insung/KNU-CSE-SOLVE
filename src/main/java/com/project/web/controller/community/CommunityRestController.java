package com.project.web.controller.community;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.board.rest.SaveBoardRequestDto;
import com.project.web.controller.community.dto.board.rest.SaveBoardResponseDto;
import com.project.web.controller.community.dto.comment.rest.*;
import com.project.web.controller.community.dto.post.rest.*;
import com.project.web.exception.Error400Exception;
import com.project.web.service.board.BoardService;
import com.project.web.service.board.CommentService;
import com.project.web.service.board.PostService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// Community(게시판/게시글/댓글) REST API
@RestController
@RequiredArgsConstructor
public class CommunityRestController {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/save-post")
    public ResponseEntity<SavePostResponseDto> savePost(@Valid @RequestBody SavePostRequestDto savePostRequestDto,
                                                        @AuthenticationPrincipal PrincipalDetails principal,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        String boardType = savePostRequestDto.getBoardType();
        String title = savePostRequestDto.getPostTitle();
        String body = savePostRequestDto.getPostBody();
        Boolean isNotice = savePostRequestDto.getPostIsNotice();

        // post 저장, 저장 후 request를 통해 제공받은 boardType을 반환. (redirect를 위함)
        BoardDto boardDto = boardService.getBoard(boardType);
        Integer boardId = boardDto.getId();
        Integer userId = principal.getUserId();
        postService.savePost(userId, boardId, title, body, isNotice);

        return ResponseEntity.ok(
                SavePostResponseDto.builder()
                        .boardType(boardType)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#updatePostRequestDto.postAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/api/update-post")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@Valid @RequestBody UpdatePostRequestDto updatePostRequestDto,
                                                            @AuthenticationPrincipal PrincipalDetails principal,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        String boardType = updatePostRequestDto.getBoardType();
        Integer postId = updatePostRequestDto.getPostId();
        String title = updatePostRequestDto.getPostTitle();
        String body = updatePostRequestDto.getPostBody();
        Boolean isNotice = updatePostRequestDto.getPostIsNotice();

        // 입력받은 boardType이 존재하는지 확인 후, post를 update함.
        // save-post와 달리 boardId가 필요하지 않음. 게시글의 수정은 postId만 필요함. (게시글의 수정은 게시판의 게시글 개수에 영향을 끼치지 않기 때문.)
        boardService.existsByBoardType(boardType);
        postService.updatePost(postId, title, body, isNotice);

        return ResponseEntity.ok(
                UpdatePostResponseDto.builder()
                        .boardType(boardType)
                        .postId(postId)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#deletePostRequestDto.postAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/api/delete-post")
    public ResponseEntity<DeletePostResponseDto> deletePost(@Valid @RequestBody DeletePostRequestDto deletePostRequestDto,
                                                            @AuthenticationPrincipal PrincipalDetails principal,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        String boardType = deletePostRequestDto.getBoardType();
        Integer postId = deletePostRequestDto.getPostId();

        // post 삭제, 삭제 후 request를 통해 제공받은 boardType을 반환. (redirect를 위함)
        BoardDto boardDto = boardService.getBoard(boardType);
        Integer boardId = boardDto.getId();
        postService.deletePost(boardId, postId);

        return ResponseEntity.ok(
                DeletePostResponseDto.builder()
                        .boardType(boardType)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/save-comment")
    public ResponseEntity<SaveCommentResponseDto> saveComment(@Valid @RequestBody SaveCommentRequestDto saveCommentRequestDto,
                                                              @AuthenticationPrincipal PrincipalDetails principal,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        Integer postId = saveCommentRequestDto.getPostId();
        Integer parentCommentId = saveCommentRequestDto.getParentCommentId();
        Integer currentPageNumber = saveCommentRequestDto.getCurrentPageNumber();
        String commentBody = saveCommentRequestDto.getCommentBody();
        Integer userId = principal.getUserId();

        // comment 저장.
        commentService.saveComment(userId, postId, parentCommentId, commentBody);

        // 저장 후 보여줄 댓글 페이지의 번호를 계산함.
        Integer totalCommentCount = postService.getTotalCommentCount(postId);
        Integer pageNumber =
                parentCommentId == null ?
                        ((totalCommentCount - 1) / CommunityConstants.COMMENT_PAGE_SIZE) + 1 :
                        currentPageNumber;

        return ResponseEntity.ok(
                SaveCommentResponseDto.builder()
                        .pageNumber(pageNumber)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and ((#deleteCommentRequestDto.commentAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/api/delete-comment")
    public ResponseEntity<DeleteCommentResponseDto> deleteComment(@Valid @RequestBody DeleteCommentRequestDto deleteCommentRequestDto,
                                                                  @AuthenticationPrincipal PrincipalDetails principal,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        Integer postId = deleteCommentRequestDto.getPostId();
        Integer commentId = deleteCommentRequestDto.getCommentId();
        Integer currentPageNumber = deleteCommentRequestDto.getCurrentPageNumber();

        // comment 삭제.
        commentService.deleteComment(postId, commentId);

        // 삭제 후 보여줄 댓글 페이지의 번호를 계산함.
        Integer totalCommentCount = postService.getTotalCommentCount(postId);
        Integer totalPageNumber = ((totalCommentCount - 1) / CommunityConstants.COMMENT_PAGE_SIZE) + 1;
        Integer pageNumber = totalPageNumber < currentPageNumber ? totalPageNumber : currentPageNumber;

        return ResponseEntity.ok(
                DeleteCommentResponseDto.builder()
                        .pageNumber(pageNumber)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/inc-post-recommend")
    public ResponseEntity<IncPostRecommendResponseDto> incPostRecommend(@Valid @RequestBody IncPostRecommendRequestDto incPostRecommendRequestDto,
                                                                        @AuthenticationPrincipal PrincipalDetails pricipal,
                                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        Integer userId = pricipal.getUserId();
        Integer postId = incPostRecommendRequestDto.getPostId();
        IncPostRecommendResponseDto incPostRecommendResponseDto = postService.incPostRecommend(userId, postId);
        return ResponseEntity.ok(incPostRecommendResponseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/inc-comment-recommend")
    public ResponseEntity<IncCommentRecommendResponseDto> incPostRecommend(@RequestBody IncCommentRecommendRequestDto incCommentRecommendRequestDto,
                                                                           @AuthenticationPrincipal PrincipalDetails principal,
                                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        Integer userId = principal.getUserId();
        Integer commentId = incCommentRecommendRequestDto.getCommentId();
        IncCommentRecommendResponseDto incCommentRecommendResponseDto = commentService.incCommentRecommend(userId, commentId);
        return ResponseEntity.ok(incCommentRecommendResponseDto);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @PostMapping("/api/save-board")
    public ResponseEntity<SaveBoardResponseDto> saveBoard(@RequestBody SaveBoardRequestDto saveBoardRequestDto,
                                                          @AuthenticationPrincipal PrincipalDetails principal,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new Error400Exception(errorMessage);
        }

        // 게시판의 생성은 어드민에게만 권한이 있음.
        String type = saveBoardRequestDto.getBoardType();
        String description = saveBoardRequestDto.getBoardDescription();
        String alias = saveBoardRequestDto.getBoardAlias();
        String category = saveBoardRequestDto.getBoardCategory();

        boardService.saveBoard(type, alias, description, category);

        return ResponseEntity.ok(
                SaveBoardResponseDto.builder()
                        .boardType(type)
                        .build()
        );
    }
}
