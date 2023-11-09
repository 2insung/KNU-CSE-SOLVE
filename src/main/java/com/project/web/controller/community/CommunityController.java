package com.project.web.controller.community;

import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.controller.community.dto.board.BoardDto;
import com.project.web.controller.community.dto.comment.CommentDto;
import com.project.web.controller.community.dto.post.*;
import com.project.web.controller.user.UserInfo;
import com.project.web.controller.user.dto.UserDto;
import com.project.web.service.board.BoardService;
import com.project.web.service.board.CommentService;
import com.project.web.service.board.PostService;
import com.project.web.service.board.S3UploaderService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;
    private final S3UploaderService s3UploaderService;

    @GetMapping("/")
    public String root(@AuthenticationPrincipal PrincipalDetails principal,
                       Model model) {
        UserDto userDto = UserInfo.getUserInfo(principal);
        model.addAttribute("user", userDto);
        return "RootPage";
    }

    @GetMapping("/board/{boardType}")
    public String board(@PathVariable(name = "boardType") String boardType,
                        @RequestParam(name = "hot", defaultValue = "false") Boolean isViewHotPostPreviewList,
                        @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
                        @AuthenticationPrincipal PrincipalDetails principal,
                        Model model) {
        // 현재 로그인한 유저의 정보
        UserDto userDto = UserInfo.getUserInfo(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        BoardDto boardDto = boardService.getBoardWithPostCount(boardType);
        model.addAttribute("board", boardDto);
        model.addAttribute("boardIsViewHotPostPreviewList", isViewHotPostPreviewList);
        Integer postCount = boardDto.getPostCount();
        Integer processedPageNumber = PageUtil.processPageNumber(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/board/" + boardType + "?page=" + processedPageNumber;
        }

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> pageNumberList = PageUtil.makePageNumberList(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        Integer prevPageNumber = PageUtil.makePrevPageNumber(pageNumber);
        Integer nextPageNumber = PageUtil.makeNextPageNumber(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("boardPageNumber", pageNumber);
        model.addAttribute("boardPageNumberList", pageNumberList);
        model.addAttribute("boardPrevPageNumber", prevPageNumber);
        model.addAttribute("boardNextPageNumber", nextPageNumber);

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
        UserDto userDto = UserInfo.getUserInfo(principal);
        model.addAttribute("user", userDto);

        // 현재 게시판의 정보를 제공하고, 현재 페이지의 값에 따라 현재 페이지의 이동을 결정함.
        BoardDto boardDto = boardService.getBoardWithPostCount(boardType);
        model.addAttribute("board", boardDto);
        Integer postCount = boardDto.getPostCount();
        Integer processedPageNumber = PageUtil.processPageNumber(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        if (!pageNumber.equals(processedPageNumber)) {
            return "redirect:/board/" + boardType + "/post/" + postId + "?page=" + processedPageNumber;
        }

        // 게시글에 대한 정보를 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
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
        List<Integer> commentPageNumberList = PageUtil.makePageNumberList(commentCount, Constants.COMMENT_PAGE_SIZE, 1);
        Integer commentPrevPageNumber = PageUtil.makePrevPageNumber(1);
        Integer commentNextPageNumber = PageUtil.makeNextPageNumber(commentCount, Constants.COMMENT_PAGE_SIZE, 1);
        model.addAttribute("commentPageNumber", 1);
        model.addAttribute("commentPageNumberList", commentPageNumberList);
        model.addAttribute("commentPrevPageNumber", commentPrevPageNumber);
        model.addAttribute("commentNextPageNumber", commentNextPageNumber);

        // 게시판의 게시글 프리뷰 리스트를 제공. 이전 /board 에서 인기글을 선택한 후 게시글을 선택하면 isViewHotPostPreviewList가 true임.
        Integer boardId = boardDto.getBoardId();
        List<PostPreviewDto> postPreviewDtoList =
                isViewHotPostPreviewList ?
                        postService.getHotPostPreviewListByBoardId(boardId, Constants.POST_PAGE_SIZE, pageNumber) :
                        postService.getPostPreviewListByBoardId(boardId, Constants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("postPreviewList", postPreviewDtoList);

        // 게시판의 페이지 리스트에 대한 정보를 제공. (현재 페이지, 페이지 리스트, 이전 페이지, 다음 페이지)
        List<Integer> boardPageNumberList = PageUtil.makePageNumberList(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        Integer boardPrevPageNumber = PageUtil.makePrevPageNumber(pageNumber);
        Integer boardNextPageNumber = PageUtil.makeNextPageNumber(postCount, Constants.POST_PAGE_SIZE, pageNumber);
        model.addAttribute("boardPageNumber", pageNumber);
        model.addAttribute("boardPageNumberList", boardPageNumberList);
        model.addAttribute("boardPrevPageNumber", boardPrevPageNumber);
        model.addAttribute("boardNextPageNumber", boardNextPageNumber);

        return "PostPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write/{boardType}")
    public String write(@PathVariable(name = "boardType") String boardType,
                        @AuthenticationPrincipal PrincipalDetails principal,
                        Model model) {
        UserDto userDto = UserInfo.getUserInfo(principal);
        model.addAttribute("user", userDto);

        BoardDto boardDto = boardService.getBoard(boardType);
        model.addAttribute("board", boardDto);

        Boolean hasNotificationAccess = postService.hasNoticeAccess(principal.getLevel());
        model.addAttribute("hasNoticeAccess", hasNotificationAccess);

        return "WritePage";
    }

    @PreAuthorize("isAuthenticated() and #postAuthor == authentication.principal.nickname")
    @GetMapping("/rewrite/{postAuthor}/{boardType}/{postId}")
    public String rewrite(@PathVariable(name = "postAuthor") String postAuthor,
                          @PathVariable(name = "boardType") String boardType,
                          @PathVariable(name = "postId") Integer postId,
                          @AuthenticationPrincipal PrincipalDetails principal,
                          Model model) {
        UserDto userDto = UserInfo.getUserInfo(principal);
        model.addAttribute("user", userDto);

        BoardDto boardDto = boardService.getBoard(boardType);
        model.addAttribute("board", boardDto);


        Boolean hasNotificationAccess = postService.hasNoticeAccess(principal.getLevel());
        model.addAttribute("hasNoticeAccess", hasNotificationAccess);

        return "WritePage";
    }

    @GetMapping("/read-comment/{postId}")
    public String viewComment(@PathVariable(name = "postId") Integer postId,
                              @RequestParam(name = "page") Integer page,
                              @AuthenticationPrincipal PrincipalDetails principal,
                              Model model) {
        // 현재 페이지의 댓글 리스트의 정보 제공.
        Integer userId = principal != null ? principal.getUserId() : null;
        List<CommentDto> commentDtoList = commentService.getCommentList(userId, postId, Constants.COMMENT_PAGE_SIZE, page);
        model.addAttribute("commentList", commentDtoList);

        // 실제 댓글 수에 대한 정보를 제공.
        PostCommentCountDto postCommentCountDto = postService.getPostCommentCount(postId);
        Integer commentCount = postCommentCountDto.getCommentCount();
        Integer totalCommentCount = postCommentCountDto.getTotalCommentCount();
        model.addAttribute("commentCount", commentCount);

        // 게시글의 id를 제공.
        model.addAttribute("postId", postId);

        // 게시글의 댓글 페이지 리스트에 대한 정보를 제공. (현재 댓글 페이지, 댓글 페이지 리스트, 이전 댓글 페이지, 다음 댓글 페이지)
        List<Integer> commentPageNumberList = PageUtil.makePageNumberList(totalCommentCount, Constants.COMMENT_PAGE_SIZE, page);
        Integer commentPrevPageNumber = PageUtil.makePrevPageNumber(page);
        Integer commentNextPageNumber = PageUtil.makeNextPageNumber(totalCommentCount, Constants.COMMENT_PAGE_SIZE, page);
        model.addAttribute("commentPageNumberList", commentPageNumberList);
        model.addAttribute("commentPageNumber", page);
        model.addAttribute("commentPrevPageNumber", commentPrevPageNumber);
        model.addAttribute("commentNextPageNumber", commentNextPageNumber);

        return "PostPage :: #commentFragment";
    }

    @PostMapping("/image/upload")
    public ModelAndView image(MultipartHttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("jsonView");
        MultipartFile uploadFile = request.getFile("upload");

        String url = s3UploaderService.upload(uploadFile, "tempImages");
        modelAndView.addObject("uploaded", true);
        modelAndView.addObject("url", url);

        return modelAndView;
    }

}
