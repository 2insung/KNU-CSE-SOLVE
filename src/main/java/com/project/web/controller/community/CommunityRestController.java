package com.project.web.controller.community;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.board.SaveBoardRequestDto;
import com.project.web.controller.community.dto.board.SaveBoardResponseDto;
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

    @PreAuthorize("isAuthenticated() and ((#updatePostRequestDto.postAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @PatchMapping("/api/update-post")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@RequestBody UpdatePostRequestDto updatePostRequestDto,
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

    @PreAuthorize("isAuthenticated() and ((#deletePostRequestDto.postAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/api/delete-post")
    public ResponseEntity<DeletePostResponseDto> deletePost(@RequestBody DeletePostRequestDto deletePostRequestDto,
                                                            @AuthenticationPrincipal PrincipalDetails principal) {
        String boardType = deletePostRequestDto.getBoardType();
        Integer postId = deletePostRequestDto.getPostId();

        BoardDto boardDto = boardService.getBoard(boardType);
        Integer boardId = boardDto.getBoardId();
        postService.deletePost(boardId, postId);

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

    @PreAuthorize("isAuthenticated() and ((#deleteCommentRequestDto.commentAuthorId == authentication.principal.userId) or hasRole('ROLE_ADMIN'))")
    @DeleteMapping("/api/delete-comment")
    public ResponseEntity<DeleteCommentResponseDto> deleteComment(@RequestBody DeleteCommentRequestDto deleteCommentRequestDto,
                                                                  @AuthenticationPrincipal PrincipalDetails principal) {
        Integer postId = deleteCommentRequestDto.getPostId();
        Integer commentId = deleteCommentRequestDto.getCommentId();
        Integer currentPageNumber = deleteCommentRequestDto.getCurrentPageNumber();
        commentService.deleteComment(postId, commentId);

        Integer totalCommentCount = postService.getTotalCommentCount(postId);
        Integer totalPageNumber = ((totalCommentCount - 1) / Constants.COMMENT_PAGE_SIZE) + 1;
        Integer commentPageNumber = totalPageNumber > currentPageNumber ? currentPageNumber : totalPageNumber;

        return ResponseEntity.ok(
                DeleteCommentResponseDto.builder()
                        .commentPageNumber(commentPageNumber)
                        .build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/inc-post-recommend")
    public ResponseEntity<IncPostRecommendResponseDto> incPostRecommend(@RequestBody IncPostRecommendRequestDto incPostRecommendRequestDto,
                                                                        @AuthenticationPrincipal PrincipalDetails pricipal) {
        Integer userId = pricipal.getUserId();
        Integer postId = incPostRecommendRequestDto.getPostId();
        IncPostRecommendResponseDto incPostRecommendResponseDto = postService.incPostRecommend(userId, postId);
        return ResponseEntity.ok(incPostRecommendResponseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/inc-comment-recommend")
    public ResponseEntity<IncCommentRecommendResponseDto> incPostRecommend(@RequestBody IncCommentRecommendRequestDto incCommentRecommendRequestDto,
                                                                           @AuthenticationPrincipal PrincipalDetails principal) {
        Integer userId = principal.getUserId();
        Integer commentId = incCommentRecommendRequestDto.getCommentId();
        IncCommentRecommendResponseDto incCommentRecommendResponseDto = commentService.incCommentRecommend(userId, commentId);
        return ResponseEntity.ok(incCommentRecommendResponseDto);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @PostMapping("/api/save-board")
    public ResponseEntity<SaveBoardResponseDto> saveBoard(@RequestBody SaveBoardRequestDto saveBoardRequestDto,
                                                          @AuthenticationPrincipal PrincipalDetails principal) {
        String type = saveBoardRequestDto.getType();
        String description = saveBoardRequestDto.getDescription();
        String alias = saveBoardRequestDto.getAlias();
        String category = saveBoardRequestDto.getCategory();

        boardService.saveBoard(type, alias, description, category);

        return ResponseEntity.ok(
                SaveBoardResponseDto.builder()
                        .boardType(type)
                        .build()
        );
    }
}
