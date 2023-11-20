package com.project.web.controller.community;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.auth.dto.UserDto;
import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.board.BoardMenuDto;
import com.project.web.controller.community.dto.board.BoardPreviewDto;
import com.project.web.controller.community.dto.comment.CommentDto;
import com.project.web.controller.community.dto.comment.CommentPageNumberDto;
import com.project.web.controller.community.dto.post.*;
import com.project.web.service.auth.UserService;
import com.project.web.service.community.BoardService;
import com.project.web.service.community.CommentService;
import com.project.web.service.community.PostService;
import com.project.web.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Community(게시판/게시글/댓글) 관련 뷰 반환
@Controller
@RequiredArgsConstructor
public class CommunityController {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/")
    public String root(@AuthenticationPrincipal PrincipalDetails principal,
                       Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // board id가 1에서 6인 게시판의 상위 10개(등록 시간 기준)의 게시글
        // 총 60개의 게시글이 존재함, board id로 분류함.
        List<TopBoardDto> topBoardDtoList = postService.getTopBoardDtos();
        model.addAttribute("topBoardList", topBoardDtoList);

        // 모든 게시판에서 상위 20개(인기글 등록 시간 기준)의 인기글
        List<TopHotPostDto> topHotPostDtoList = postService.getTopHotPostDtos();
        model.addAttribute("topHotPostList", topHotPostDtoList);

        return "community/RootPage";
    }

    @GetMapping("/board/{boardType}")
    public String board(@PathVariable(name = "boardType") String boardType,
                        @RequestParam(name = "hot", defaultValue = "false") Boolean isViewHotPostPreviewList,
                        @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                        @AuthenticationPrincipal PrincipalDetails principal,
                        Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        // isViewHotPostPreviewList가 true인 경우는 인기글만 볼 수 있음.
        BoardDto boardDto = boardService.getBoardWithPostCount(boardType);
        model.addAttribute("board", boardDto);
        model.addAttribute("boardIsViewHotPostPreviewList", isViewHotPostPreviewList);
        Integer boardPostCount = isViewHotPostPreviewList ? boardDto.getHotPostCount() : boardDto.getPostCount();
        Integer processedPageNumber = PageUtil.processPageNumber(boardPostCount, CommunityConstants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            if (isViewHotPostPreviewList) {
                return "redirect:/board/" + boardType + "?hot=" + isViewHotPostPreviewList + "&page=" + processedPageNumber;
            }
            else {
                return "redirect:/board/" + boardType + "?page=" + processedPageNumber;
            }

        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 게시글 페이지, 게시글 페이지 리스트)
        List<Integer> postPageNumberList = PageUtil.makePageNumberList(boardPostCount, CommunityConstants.POST_PAGE_SIZE, pageNumber);
        List<PostPageNumberDto> postPageNumberDtoList =
                postPageNumberList.stream().map(
                        (number) -> PostPageNumberDto.builder()
                                .boardType(boardType)
                                .pageNumber(number)
                                .build()
                ).collect(Collectors.toList());
        model.addAttribute("postPageNumber", pageNumber);
        model.addAttribute("postPageNumberList", postPageNumberDtoList);

        // 게시판의 게시글 프리뷰 리스트를 제공. 만약 '인기글' 선택 시 isViewHotPostPreviewList가 true임.
        Integer boardId = boardDto.getId();
        List<PostPreviewDto> postPreviewDtoList =
                isViewHotPostPreviewList ?
                        postService.getHotPostPreviewDtosByBoardId(boardId, CommunityConstants.POST_PAGE_SIZE, pageNumber) :
                        postService.getPostPreviewDtosByBoardId(boardId, CommunityConstants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("postPreviewList", postPreviewDtoList);

        return "community/BoardPage";
    }

    @GetMapping("/board/{boardType}/post/{postId}")
    public String post(@PathVariable(name = "boardType") String boardType,
                       @PathVariable(name = "postId") Integer postId,
                       @RequestParam(name = "hot", defaultValue = "false") Boolean isViewHotPostPreviewList,
                       @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                       @AuthenticationPrincipal PrincipalDetails principal,
                       Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        // isViewHotPostPreviewList가 true인 경우는 인기글만 볼 수 있음.
        BoardDto boardDto = boardService.getBoardWithPostCount(boardType);
        model.addAttribute("board", boardDto);
        model.addAttribute("boardIsViewHotPostPreviewList", isViewHotPostPreviewList);
        Integer boardPostCount = isViewHotPostPreviewList ? boardDto.getHotPostCount() : boardDto.getPostCount();
        Integer processedPageNumber = PageUtil.processPageNumber(boardPostCount, CommunityConstants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            if (isViewHotPostPreviewList) {
                return "redirect:/board/" + boardType + "/post/" + postId + "?hot=" + isViewHotPostPreviewList + "&page=" + processedPageNumber;
            }
            else {
                return "redirect:/board/" + boardType + "/post/" + postId + "?page=" + processedPageNumber;
            }
        }

        // 게시글에 대한 정보 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
        PostDto postDto = postService.getPostDto(userId, postId);
        model.addAttribute("post", postDto);

        // 게시글의 현재 댓글 수에 대한 정보를 제공. (타임리프 변수로 활용할 commentCount 댓글에 대한 CRUD 동작에 동적으로 변하기 때문에 따로 변수로 설정했음.)
        // commentCount는 삭제된 댓글을 제외한 댓글 수임. totalCommentCount는 삭제된 댓글을 제외하되, 자식 댓글이 존재하면서 삭제된 댓글들만 예외로 포함한 댓글수임.
        Integer commentCount = postDto.getCommentCount();
        Integer totalCommentCount = postDto.getTotalCommentCount();
        model.addAttribute("commentCount", commentCount);

        // 게시글의 댓글 페이지에 해당하는 댓글 리스트에 대한 정보를 제공.
        List<CommentDto> commentDtoList = commentService.getCommentDtos(userId, postId, CommunityConstants.COMMENT_PAGE_SIZE, 1);
        model.addAttribute("commentList", commentDtoList);

        // 게시글의 댓글 페이지 리스트에 대한 정보를 제공. (현재 댓글 페이지, 댓글 페이지 리스트)
        List<Integer> commentPageNumberList = PageUtil.makePageNumberList(totalCommentCount, CommunityConstants.COMMENT_PAGE_SIZE, 1);
        List<CommentPageNumberDto> commentPageNumberDtoList =
                commentPageNumberList.stream().map(
                        (number) -> CommentPageNumberDto.builder()
                                .postId(postId)
                                .pageNumber(number)
                                .build()
                ).collect(Collectors.toList());
        model.addAttribute("commentPageNumber", 1);
        model.addAttribute("commentPageNumberList", commentPageNumberDtoList);

        // 게시판의 게시글 프리뷰 리스트를 제공. 이전 /board 에서 인기글을 선택한 후 게시글을 선택하면 isViewHotPostPreviewList가 true임.
        Integer boardId = boardDto.getId();
        List<PostPreviewDto> postPreviewDtoList =
                isViewHotPostPreviewList ?
                        postService.getHotPostPreviewDtosByBoardId(boardId, CommunityConstants.POST_PAGE_SIZE, pageNumber) :
                        postService.getPostPreviewDtosByBoardId(boardId, CommunityConstants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("postPreviewList", postPreviewDtoList);

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 게시글 페이지, 게시글 페이지 리스트)
        List<Integer> postPageNumberList = PageUtil.makePageNumberList(boardPostCount, CommunityConstants.POST_PAGE_SIZE, pageNumber);
        List<PostPageNumberDto> postPageNumberDtoList =
                postPageNumberList.stream().map(
                        (number) -> PostPageNumberDto.builder()
                                .boardType(boardType)
                                .pageNumber(number)
                                .build()
                ).collect(Collectors.toList());
        model.addAttribute("postPageNumber", pageNumber);
        model.addAttribute("postPageNumberList", postPageNumberDtoList);

        // 게시글 조회수 1 증가
        postService.incPostHit(postId);

        model.addAttribute("postId", postId);

        return "community/PostPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write/{boardType}")
    public String write(@PathVariable(name = "boardType") String boardType,
                        @AuthenticationPrincipal PrincipalDetails principal,
                        Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보
        BoardDto boardDto = boardService.getBoardDto(boardType);
        model.addAttribute("board", boardDto);

        // 현재 게시글을 작성하는 사용자가 '공지사항'을 올릴 권한이 있는지에 대한 정보 제공.
        Boolean hasNoticeAccess = postService.hasNoticeAccess(principal.getRole());
        model.addAttribute("hasNoticeAccess", hasNoticeAccess);

        // 현재 게시글을 수정하려는 상태인지에 대한 정보 제공. /write는 수정이 아닌 새로운 게시글 등록임.
        model.addAttribute("isRewrite", false);

        return "community/WritePage";
    }

    @PreAuthorize("isAuthenticated() and #postAuthorId == authentication.principal.userId")
    @GetMapping("/rewrite/{postAuthorId}/{boardType}/{postId}")
    public String rewrite(@PathVariable(name = "postAuthorId") Integer postAuthorId,
                          @PathVariable(name = "boardType") String boardType,
                          @PathVariable(name = "postId") Integer postId,
                          @AuthenticationPrincipal PrincipalDetails principal,
                          Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보
        BoardDto boardDto = boardService.getBoardDto(boardType);
        model.addAttribute("board", boardDto);

        // 게시글 수정을 위해 수정하려는 게시글의 이전 정보를 제공.
        RewriteDto rewriteDto = postService.getRewriteDto(postId);
        model.addAttribute("rewrite", rewriteDto);

        // 현재 게시글을 작성하는 사용자가 '공지사항'을 올릴 권한이 있는지에 대한 정보 제공.
        Boolean hasNotificationAccess = postService.hasNoticeAccess(principal.getRole());
        model.addAttribute("hasNoticeAccess", hasNotificationAccess);

        // 현재 게시글을 수정하려는 상태인지에 대한 정보 제공. /rewrite는 수정임.
        model.addAttribute("isRewrite", true);

        return "community/WritePage";
    }

    @GetMapping("/read-comment/{postId}")
    public String readComment(@PathVariable(name = "postId") Integer postId,
                              @RequestParam(name = "page") Integer pageNumber,
                              @AuthenticationPrincipal PrincipalDetails principal,
                              Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // 현재 페이지의 댓글 리스트의 정보 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
        List<CommentDto> commentDtoList = commentService.getCommentDtos(userId, postId, CommunityConstants.COMMENT_PAGE_SIZE, pageNumber);
        model.addAttribute("commentList", commentDtoList);

        // 실제 댓글 수에 대한 정보를 제공.
        PostCommentCountDto postCommentCountDto = postService.getPostCommentCountDto(postId);
        Integer commentCount = postCommentCountDto.getCommentCount();
        Integer totalCommentCount = postCommentCountDto.getTotalCommentCount();
        model.addAttribute("commentCount", commentCount);

        // 게시글의 댓글 페이지 리스트에 대한 정보를 제공. (현재 댓글 페이지, 댓글 페이지 리스트, 이전 댓글 페이지, 다음 댓글 페이지)
        List<Integer> commentPageNumberList = PageUtil.makePageNumberList(totalCommentCount, CommunityConstants.COMMENT_PAGE_SIZE, pageNumber);
        List<CommentPageNumberDto> commentPageNumberDtoList = commentPageNumberList.stream().map(
                (number) -> CommentPageNumberDto.builder()
                        .postId(postId)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("commentPageNumberList", commentPageNumberDtoList);
        model.addAttribute("commentPageNumber", pageNumber);

        model.addAttribute("postId", postId);

        return "community/PostPage :: #commentFragment";
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @GetMapping("/manage-board")
    public String manageBoard(@AuthenticationPrincipal PrincipalDetails principal,
                              Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);
        // 게시판 생성 및 관리는 어드민에게만 권한이 있음.

        return "community/ManageBoardPage";
    }

    @GetMapping("/all-board")
    public String allBoard(@AuthenticationPrincipal PrincipalDetails principal,
                           Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUser(principal);
        model.addAttribute("user", userDto);

        // 게시판에 대한 정보를 제공함. 일반, 과목으로 분류됨.
        List<BoardPreviewDto> boardPreviewDtoList = boardService.getBoardPreviewDtos();
        List<BoardPreviewDto> generalBoardList = boardPreviewDtoList.stream()
                .filter(board -> "일반".equals(board.getCategory()))
                .collect(Collectors.toList());
        List<BoardPreviewDto> subjectBoardList = boardPreviewDtoList.stream()
                .filter(board -> "과목".equals(board.getCategory()))
                .collect(Collectors.toList());
        model.addAttribute("generalBoardList", generalBoardList);
        model.addAttribute("subjectBoardList", subjectBoardList);

        return "community/AllBoardPage";
    }

    @GetMapping("/board-menu")
    public String boardList(Model model) {
        List<BoardPreviewDto> boardPreviewDtoList = boardService.getTopThirtyBoardPreviewDtos();
        int totalBoardCount = boardPreviewDtoList.size();
        int chunkCount = totalBoardCount / 6;

        List<BoardMenuDto> boardMenuDtoList = new ArrayList<>();
        for (int i = 0; i < chunkCount; i++) {
            boardMenuDtoList.add(
                    BoardMenuDto.builder()
                            .number(i)
                            .boardPreviewList(boardPreviewDtoList.subList(6 * i, 6 * (i + 1)))
                            .build()
            );
        }

        //Heeader의 Board Menu List에 사용됨.
        model.addAttribute("boardMenuList", boardMenuDtoList);

        return "common/Header :: #boardMenuList";
    }
}
