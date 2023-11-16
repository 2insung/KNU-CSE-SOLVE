package com.project.web.controller.community;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.auth.dto.UserDto;
import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.board.BoardPreviewDto;
import com.project.web.controller.community.dto.comment.CommentDto;
import com.project.web.controller.community.dto.comment.CommentPageNumberDto;
import com.project.web.controller.community.dto.post.*;
import com.project.web.service.auth.UserService;
import com.project.web.service.board.BoardService;
import com.project.web.service.board.CommentService;
import com.project.web.service.board.PostService;
import com.project.web.service.upload.ImageUploadService;
import com.project.web.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommunityController {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;
    private final ImageUploadService imageUploadService;
    private final UserService userService;

    @GetMapping("/")
    public String root(@AuthenticationPrincipal PrincipalDetails principal,
                       Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        List<TopPostListDto> topPostDtoList = postService.getTopPost();
        model.addAttribute("topPostList", topPostDtoList);

        List<TopHotPostDto> topHotPostDtoList = postService.getTopHotPost();
        model.addAttribute("topHotPostList", topHotPostDtoList);
        return "RootPage";
    }

    @GetMapping("/board/{boardType}")
    public String board(@PathVariable(name = "boardType") String boardType,
                        @RequestParam(name = "hot", defaultValue = "false") Boolean isViewHotPostPreviewList,
                        @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                        @AuthenticationPrincipal PrincipalDetails principal,
                        Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        BoardDto boardDto = boardService.getBoardWithPostCount(boardType);
        model.addAttribute("board", boardDto);
        model.addAttribute("boardIsViewHotPostPreviewList", isViewHotPostPreviewList);
        Integer boardPostCount = !isViewHotPostPreviewList ? boardDto.getPostCount() : boardDto.getHotPostCount();
        Integer processedPageNumber = PageUtil.processPageNumber(boardPostCount, Constants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/board/" + boardType + "?page=" + processedPageNumber;
        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> postPageNumberList = PageUtil.makePageNumberList(boardPostCount, Constants.POST_PAGE_SIZE, pageNumber);
        List<PostPageNumberDto> postPageNumberDtoList = postPageNumberList.stream().map(
                (number) -> PostPageNumberDto.builder()
                        .boardType(boardType)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("postPageNumberList", postPageNumberDtoList);
        model.addAttribute("postPageNumber", pageNumber);

        // 게시판의 게시글 프리뷰 리스트를 제공. 만약 '인기글' 선택 시 isViewHotPostPreviewList가 true임.
        Integer boardId = boardDto.getBoardId();
        List<PostPreviewDto> postPreviewDtoList =
                isViewHotPostPreviewList ?
                        postService.getHotPostPreviewListByBoardId(boardId, Constants.POST_PAGE_SIZE, pageNumber) :
                        postService.getPostPreviewListByBoardId(boardId, Constants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("postPreviewList", postPreviewDtoList);

        return "BoardPage";
    }

    @GetMapping("/board/{boardType}/post/{postId}")
    public String post(@PathVariable(name = "boardType") String boardType,
                       @PathVariable(name = "postId") Integer postId,
                       @RequestParam(name = "hot", defaultValue = "false") Boolean isViewHotPostPreviewList,
                       @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                       @AuthenticationPrincipal PrincipalDetails principal,
                       Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        BoardDto boardDto = boardService.getBoardWithPostCount(boardType);
        model.addAttribute("board", boardDto);
        model.addAttribute("boardIsViewHotPostPreviewList", isViewHotPostPreviewList);
        Integer postCount = boardDto.getPostCount();
        Integer processedPageNumber = PageUtil.processPageNumber(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/board/" + boardType + "/post/" + postId + "?page=" + processedPageNumber;
        }

        // 게시글에 대한 정보를 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
        postService.incPostHit(postId);
        PostDto postDto = postService.getPost(userId, postId);
        model.addAttribute("post", postDto);

        // 게시글의 id를 제공.
        model.addAttribute("postId", postId);

        // 게시글의 현재 댓글 수에 대한 정보를 제공. (타임리프 변수로 활용할 commentCount 댓글에 대한 CRUD 동작에 동적으로 변하기 때문에 따로 변수로 설정했음.)
        // commentCount는 삭제된 댓글을 제외한 댓글 수임. totalCommentCount는 삭제된 댓글을 제외하되, 자식 댓글이 존재하면서 삭제된 댓글들만 예외로 포함한 댓글수임.
        Integer commentCount = postDto.getCommentCount();
        Integer totalCommentCount = postDto.getTotalCommentCount();
        model.addAttribute("commentCount", commentCount);

        // 게시글의 댓글 페이지에 해당하는 댓글 리스트에 대한 정보를 제공.
        List<CommentDto> commentDtoList = commentService.getCommentList(userId, postId, Constants.COMMENT_PAGE_SIZE, 1);
        model.addAttribute("commentList", commentDtoList);

        // 게시글의 댓글 페이지 리스트에 대한 정보를 제공. (현재 댓글 페이지, 댓글 페이지 리스트, 이전 댓글 페이지, 다음 댓글 페이지)
        List<Integer> commentPageNumberList = PageUtil.makePageNumberList(totalCommentCount, Constants.COMMENT_PAGE_SIZE, 1);
        List<CommentPageNumberDto> commentPageNumberDtoList = commentPageNumberList.stream().map(
                (number) -> CommentPageNumberDto.builder()
                        .postId(postId)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("commentPageNumberList", commentPageNumberDtoList);
        model.addAttribute("commentPageNumber", 1);

        // 게시판의 게시글 프리뷰 리스트를 제공. 이전 /board 에서 인기글을 선택한 후 게시글을 선택하면 isViewHotPostPreviewList가 true임.
        Integer boardId = boardDto.getBoardId();
        List<PostPreviewDto> postPreviewDtoList =
                isViewHotPostPreviewList ?
                        postService.getHotPostPreviewListByBoardId(boardId, Constants.POST_PAGE_SIZE, pageNumber) :
                        postService.getPostPreviewListByBoardId(boardId, Constants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("postPreviewList", postPreviewDtoList);

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> postPageNumberList = PageUtil.makePageNumberList(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        List<PostPageNumberDto> postPageNumberDtoList = postPageNumberList.stream().map(
                (number) -> PostPageNumberDto.builder()
                        .boardType(boardType)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("postPageNumberList", postPageNumberDtoList);
        model.addAttribute("postPageNumber", pageNumber);

        return "PostPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write/{boardType}")
    public String write(@PathVariable(name = "boardType") String boardType,
                        @AuthenticationPrincipal PrincipalDetails principal,
                        Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보
        BoardDto boardDto = boardService.getBoard(boardType);
        model.addAttribute("board", boardDto);

        // 현재 게시글을 작성하는 사용자가 '공지사항'을 올릴 권한이 있는지에 대한 정보 제공.
        Boolean hasNotificationAccess = postService.hasNoticeAccess(principal.getLevel());
        model.addAttribute("hasNoticeAccess", hasNotificationAccess);

        // 현재 게시글을 수정하려는 상태인지에 대한 정보 제공. /write는 수정이 아닌 등록임.
        model.addAttribute("isRewrite", false);

        return "WritePage";
    }

    @PreAuthorize("isAuthenticated() and #postAuthor == authentication.principal.nickname")
    @GetMapping("/rewrite/{postAuthor}/{boardType}/{postId}")
    public String rewrite(@PathVariable(name = "postAuthor") String postAuthor,
                          @PathVariable(name = "boardType") String boardType,
                          @PathVariable(name = "postId") Integer postId,
                          @AuthenticationPrincipal PrincipalDetails principal,
                          Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보
        BoardDto boardDto = boardService.getBoard(boardType);
        model.addAttribute("board", boardDto);

        // 게시글 수정을 위해 수정하려는 게시글의 정보를 제공.
        RewriteDto rewriteDto = postService.getRewrite(postId);
        model.addAttribute("rewrite", rewriteDto);

        // 현재 게시글을 작성하는 사용자가 '공지사항'을 올릴 권한이 있는지에 대한 정보 제공.
        Boolean hasNotificationAccess = postService.hasNoticeAccess(principal.getLevel());
        model.addAttribute("hasNoticeAccess", hasNotificationAccess);

        // 현재 게시글을 수정하려는 상태인지에 대한 정보 제공. /rewrite는 수정임.
        model.addAttribute("isRewrite", true);

        return "WritePage";
    }

    @GetMapping("/read-comment/{postId}")
    public String viewComment(@PathVariable(name = "postId") Integer postId,
                              @RequestParam(name = "page") Integer page,
                              @AuthenticationPrincipal PrincipalDetails principal,
                              Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);


        // 현재 페이지의 댓글 리스트의 정보 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
        List<CommentDto> commentDtoList = commentService.getCommentList(userId, postId, Constants.COMMENT_PAGE_SIZE, page);
        model.addAttribute("commentList", commentDtoList);

        // 실제 댓글 수에 대한 정보를 제공.
        PostCommentCountDto postCommentCountDto = postService.getPostCommentCount(postId);
        Integer commentCount = postCommentCountDto.getCommentCount();
        Integer totalCommentCount = postCommentCountDto.getTotalCommentCount();
        model.addAttribute("commentCount", commentCount);

        // 게시글의 댓글 페이지 리스트에 대한 정보를 제공. (현재 댓글 페이지, 댓글 페이지 리스트, 이전 댓글 페이지, 다음 댓글 페이지)
        List<Integer> commentPageNumberList = PageUtil.makePageNumberList(totalCommentCount, Constants.COMMENT_PAGE_SIZE, page);
        List<CommentPageNumberDto> commentPageNumberDtoList = commentPageNumberList.stream().map(
                (number) -> CommentPageNumberDto.builder()
                        .postId(postId)
                        .pageNumber(number)
                        .build()
        ).collect(Collectors.toList());
        model.addAttribute("commentPageNumberList", commentPageNumberDtoList);
        model.addAttribute("commentPageNumber", page);

        return "PostPage :: #commentFragment";
    }

    @PostMapping("/image/upload")
    public ModelAndView image(MultipartHttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        MultipartFile uploadFile = request.getFile("upload");

        String url = imageUploadService.uploadImage(uploadFile);
        modelAndView.addObject("uploaded", true);
        modelAndView.addObject("url", url);

        return modelAndView;
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @GetMapping("/manage-board")
    public String manageBoard(@AuthenticationPrincipal PrincipalDetails principal,
                              Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);
        return "ManageBoardPage";
    }

    @GetMapping("/all-board")
    public String allBoard(@AuthenticationPrincipal PrincipalDetails principal,
                           Model model) {
        UserDto userDto = userService.getUserDto(principal);
        model.addAttribute("user", userDto);

        List<BoardPreviewDto> boardPreviewDtoList = boardService.getBoardPreviewList();
        List<BoardPreviewDto> generalBoardList = boardPreviewDtoList.stream()
                .filter(board -> "일반".equals(board.getCategory()))
                .collect(Collectors.toList());
        List<BoardPreviewDto> subjectBoardList = boardPreviewDtoList.stream()
                .filter(board -> "과목".equals(board.getCategory()))
                .collect(Collectors.toList());
        model.addAttribute("generalBoardList", generalBoardList);
        model.addAttribute("subjectBoardList", subjectBoardList);
        return "AllBoardPage";
    }


}
