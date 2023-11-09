package com.project.web.controller.community;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.comment.*;
import com.project.web.controller.community.dto.post.*;
import com.project.web.service.board.BoardService;
import com.project.web.service.board.CommentService;
import com.project.web.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommunityRestController {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/save-post")
    public ResponseEntity<SavePostResponseDto> savePost(@RequestBody SavePostRequestDto savePostRequestDto,
                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        String boardType = savePostRequestDto.getBoardType();
        String title = savePostRequestDto.getTitle();
        String body = savePostRequestDto.getBody();
        Boolean isNotice = savePostRequestDto.getIsNotice();

        BoardDto boardDto = boardService.getBoard(boardType);
        Integer boardId = boardDto.getBoardId();
        Integer userId = principal.getUserId();
        postService.savePost(userId, boardId, title, body, isNotice);

        return ResponseEntity.ok(
                SavePostResponseDto.builder()
                        .boardType(boardType)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and #postAuthor == authentication.principal.nickname")
    @PatchMapping("/api/update-post/{postAuthor}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@RequestBody UpdatePostRequestDto updatePostRequestDto,
                                                            @PathVariable(name = "postAuthor") String postAuthor,
                                                            @AuthenticationPrincipal PrincipalDetails principal) {
        String boardType = updatePostRequestDto.getBoardType();
        Integer postId = updatePostRequestDto.getPostId();
        String title = updatePostRequestDto.getTitle();
        String body = updatePostRequestDto.getBody();
        Boolean isNotice = updatePostRequestDto.getIsNotice();

        boardService.existsByBoardType(boardType);
        postService.updatePost(postId, title, body, isNotice);

        return ResponseEntity.ok(
                UpdatePostResponseDto.builder()
                        .boardType(boardType)
                        .postId(postId)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and #postAuthor == authentication.principal.nickname")
    @DeleteMapping("/api/delete-post/{postAuthor}")
    public ResponseEntity<DeletePostResponseDto> deletePost(@RequestBody DeletePostRequestDto deletePostRequestDto,
                                                            @PathVariable(name = "postAuthor") String postAuthor,
                                                            @AuthenticationPrincipal PrincipalDetails principal) {
        String boardType = deletePostRequestDto.getBoardType();
        Integer postId = deletePostRequestDto.getPostId();

        BoardDto boardDto = boardService.getBoard(boardType);
        Integer boardId = boardDto.getBoardId();
        Integer userId = principal.getUserId();
        postService.deletePost(userId, boardId, postId);

        return ResponseEntity.ok(
                DeletePostResponseDto.builder()
                        .boardType(boardType)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/save-comment")
    public ResponseEntity<SaveCommentResponseDto> saveComment(@RequestBody SaveCommentRequestDto saveCommentRequestDto,
                                                              @AuthenticationPrincipal PrincipalDetails principal) {
        Integer userId = principal.getUserId();
        Integer postId = saveCommentRequestDto.getPostId();
        Integer parentCommentId = saveCommentRequestDto.getParentCommentId();
        Integer currentPageNumber = saveCommentRequestDto.getCurrentPageNumber();
        String commentBody = saveCommentRequestDto.getBody();
        commentService.saveComment(userId, postId, parentCommentId, commentBody);

        Integer totalCommentCount = postService.getTotalCommentCount(postId);
        Integer commentPageNumber = parentCommentId == null ?
                ((totalCommentCount - 1) / Constants.COMMENT_PAGE_SIZE) + 1 :
                currentPageNumber;

        return ResponseEntity.ok(
                SaveCommentResponseDto.builder()
                        .commentPageNumber(commentPageNumber)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated() and #commentAuthor == authentication.principal.nickname")
    @DeleteMapping("/api/delete-comment/{commentAuthor}")
    public ResponseEntity<DeleteCommentResponseDto> deleteComment(@RequestBody DeleteCommentRequestDto deleteCommentRequestDto,
                                                                  @PathVariable(name = "commentAuthor") String commentAuthor,
                                                                  @AuthenticationPrincipal PrincipalDetails principal) {
        Integer userId = principal.getUserId();
        Integer postId = deleteCommentRequestDto.getPostId();
        Integer commentId = deleteCommentRequestDto.getCommentId();
        Integer currentPageNumber = deleteCommentRequestDto.getCurrentPageNumber();
        commentService.deleteComment(userId, postId, commentId);

        Integer totalCommentCount = postService.getTotalCommentCount(postId);
        Integer totalPageNumber = ((totalCommentCount - 1) / Constants.COMMENT_PAGE_SIZE) + 1;
        Integer commentPageNumber = totalPageNumber > currentPageNumber ? currentPageNumber : totalPageNumber;

        return ResponseEntity.ok(
                DeleteCommentResponseDto.builder()
                        .commentPageNumber(commentPageNumber)
                        .build()
        );
    }
}
