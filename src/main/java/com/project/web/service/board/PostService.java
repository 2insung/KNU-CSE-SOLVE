package com.project.web.service.board;

import com.project.web.controller.community.dto.post.*;
import com.project.web.controller.community.dto.post.rest.IncPostRecommendResponseDto;
import com.project.web.domain.board.Board;
import com.project.web.domain.member.Member;
import com.project.web.domain.member.Role;
import com.project.web.domain.post.*;
import com.project.web.exception.Error400Exception;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.board.BoardPostCountRepository;
import com.project.web.repository.board.BoardRepository;
import com.project.web.repository.comment.CommentRecommendCountRepository;
import com.project.web.repository.comment.CommentRecommendMemberRepository;
import com.project.web.repository.comment.CommentRepository;
import com.project.web.repository.member.MemberRepository;
import com.project.web.repository.post.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final BoardRepository boardRepository;
    private final BoardPostCountRepository boardPostCountRepository;
    private final PostRepository postRepository;
    private final PostContentRepository postContentRepository;
    private final PostHitCountRepository postHitCountRepository;
    private final PostRecommendCountRepository postRecommendCountRepository;
    private final PostCommentCountRepository postCommentCountRepository;
    private final PostRecommendMemberRepository postRecommendMemberRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentRecommendCountRepository commentRecommendCountRepository;
    private final CommentRecommendMemberRepository commentRecommendMemberRepository;

    /*
     게시글 미리보기 출력 함수.
     * boardId에 해당하는 게시판의 게시글 미리보기를 pageSize 개수만큼 출력함.
     * pageNumber는 게시글 페이지 번호임.
     * [일반 + 공지 + 인기], 모든 카테고리에 대한 게시글을 출력함.
    */
    @Transactional(readOnly = true)
    public List<PostPreviewDto> getPostPreviewListByBoardId(Integer boardId, Integer pageSize, Integer pageNumber) {
        List<Object[]> results = postRepository.findPostPreviewByBoardId(boardId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultPostId = (Integer) result[0];
                    Integer resultAuthorId = (Integer) result[1];
                    Boolean resultIsNotice = (Boolean) result[2];
                    Boolean resultIsHot = (Boolean) result[3];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[4]).toLocalDateTime();
                    String resultAuthorNickname = (String) result[5];
                    String resultAuthorProfileImage = (String) result[6];
                    String resultTitle = (String) result[7];
                    String resultSummary = (String) result[8];
                    String resultThumbnail = (String) result[9];
                    Integer resultHitCount = (Integer) result[10];
                    Integer resultRecommendCount = (Integer) result[11];
                    Integer resultCommentCount = (Integer) result[12];

                    return PostPreviewDto.builder()
                            .id(resultPostId)
                            .authorId(resultAuthorId)
                            .isNotice(resultIsNotice)
                            .isHot(resultIsHot)
                            .createdAt(resultCreatedAt)
                            .authorNickname(resultAuthorNickname)
                            .authorProfileImage(resultAuthorProfileImage)
                            .title(resultTitle)
                            .summary(resultSummary)
                            .thumbnail(resultThumbnail)
                            .hitCount(resultHitCount)
                            .recommendCount(resultRecommendCount)
                            .commentCount(resultCommentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /*
      인기글 미리보기 출력 함수.
     * boardId에 해당하는 게시판의 게시글 미리보기를 pageSize 개수만큼 출력함.
     * pageNumber는 게시글 페이지 번호임.
     * [인기], 인기 카테고리에 대한 게시글을 출력함.
    */
    @Transactional(readOnly = true)
    public List<PostPreviewDto> getHotPostPreviewListByBoardId(Integer boardId, Integer pageSize, Integer pageNumber) {
        List<Object[]> results = postRepository.findHotPostPreviewByBoardId(boardId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultPostId = (Integer) result[0];
                    Integer resultAuthorId = (Integer) result[1];
                    Boolean resultIsNotice = (Boolean) result[2];
                    Boolean resultIsHot = (Boolean) result[3];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[4]).toLocalDateTime();
                    String resultAuthorNickname = (String) result[5];
                    String resultAuthorProfileImage = (String) result[6];
                    String resultTitle = (String) result[7];
                    String resultSummary = (String) result[8];
                    String resultThumbnail = (String) result[9];
                    Integer resultHitCount = (Integer) result[10];
                    Integer resultRecommendCount = (Integer) result[11];
                    Integer resultCommentCount = (Integer) result[12];

                    return PostPreviewDto.builder()
                            .id(resultPostId)
                            .authorId(resultAuthorId)
                            .isNotice(resultIsNotice)
                            .isHot(resultIsHot)
                            .createdAt(resultCreatedAt)
                            .authorNickname(resultAuthorNickname)
                            .authorProfileImage(resultAuthorProfileImage)
                            .title(resultTitle)
                            .summary(resultSummary)
                            .thumbnail(resultThumbnail)
                            .hitCount(resultHitCount)
                            .recommendCount(resultRecommendCount)
                            .commentCount(resultCommentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /*
      게시글 출력 함수.
     * postId에 해당하는 게시글을 출력하는 함수임.
    */
    @Transactional(readOnly = true)
    public PostDto getPost(Integer userId, Integer postId) {
        Object result = postRepository.findPostById(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        Object[] arr = (Object[]) result;
        Integer resultPostId = (Integer) arr[0];
        Integer resultAuthorId = (Integer) arr[1];
        Boolean resultIsNotice = (Boolean) arr[2];
        Boolean resultIsHot = (Boolean) arr[3];
        LocalDateTime resultCreatedAt = (LocalDateTime) arr[4];
        String resultCategory = (String) arr[5];
        String resultBoardType = (String) arr[6];
        String resultAuthorNickname = (String) arr[7];
        String resultAuthorProfileImage = (String) arr[8];
        String resultTitle = (String) arr[9];
        String resultBody = (String) arr[10];
        LocalDateTime resultUpdatedAt = (LocalDateTime) arr[11];
        Integer resultHitCount = (Integer) arr[12];
        Integer resultRecommendCount = (Integer) arr[13];
        Integer resultCommentCount = (Integer) arr[14];
        Integer resultTotalCommentCount = (Integer) arr[15];
        Boolean isMine = userId != null && userId.equals(resultAuthorId);

        return PostDto.builder()
                .id(resultPostId)
                .authorId(resultAuthorId)
                .isNotice(resultIsNotice)
                .isHot(resultIsHot)
                .createdAt(resultCreatedAt)
                .category(resultCategory)
                .boardType(resultBoardType)
                .authorNickname(resultAuthorNickname)
                .authorProfileImage(resultAuthorProfileImage)
                .title(resultTitle)
                .body(resultBody)
                .updatedAt(resultUpdatedAt)
                .hitCount(resultHitCount)
                .recommendCount(resultRecommendCount)
                .commentCount(resultCommentCount)
                .totalCommentCount(resultTotalCommentCount)
                .isMine(isMine)
                .build();
    }

    /*
      게시글 저장 함수.
     * boardId에 해당하는 게시판에 게시글을 저장하는 함수임.
     * 만약 게시할 글이 공지라면 priority 는 1임. 모든 카테고리의 게시글 중 가장 먼저 출력됨.
     * 저장 후 게시판의 게시글 수를 1 증가. (게시글을 저장하는 시점에서는 게시글이 인기글이 아니기 때문에, 인기글 수는 0 증가)
    */
    @Transactional
    public void savePost(Integer userId, Integer boardId, String title, String body, Boolean isNotice) {
        Board board = boardRepository.getReferenceById(boardId);
        Member postAuthor = memberRepository.getReferenceById(userId);

        Post post = Post.builder()
                .member(postAuthor)
                .board(board)
                .priority(isNotice ? 1 : 0)
                .isNotice(isNotice)
                .isHot(false)
                .isDeleted(false)
                .category(isNotice ? "공지" : "일반")
                .build();
        postRepository.save(post);

        Document document = Jsoup.parse(body);
        String bodyText = document.text();
        String summary = bodyText.length() > 90 ? bodyText.substring(0, 90) + "..." : bodyText;
        Element imgElement = document.select("img").first();
        String thumbnail = imgElement != null ? imgElement.attr("src") : null;
        PostContent postContent = PostContent.builder()
                .post(post)
                .title(title)
                .body(body)
                .summary(summary)
                .thumbnail(thumbnail)
                .updatedAt(null)
                .build();
        postContentRepository.save(postContent);

        PostHitCount postHitCount = PostHitCount.builder()
                .post(post)
                .hitCount(0)
                .build();
        postHitCountRepository.save(postHitCount);

        PostRecommendCount postRecommendCount = PostRecommendCount.builder()
                .post(post)
                .recommendCount(0)
                .build();
        postRecommendCountRepository.save(postRecommendCount);

        PostCommentCount postCommentCount = PostCommentCount.builder()
                .post(post)
                .commentCount(0)
                .totalCommentCount(0)
                .build();
        postCommentCountRepository.save(postCommentCount);

        if (boardPostCountRepository.updateByBoardId(boardId, 1, 0) == 0) {
            throw new Error404Exception("존재하지 않는 게시판입니다.");
        }
    }

    /*
      게시글 수정 함수.
     * postId에 해당하는 게시글을 수정하는 함수임.
     * 수정이기 때문에 게시판 글 수는 update하지 않음.
    */
    @Transactional
    public void updatePost(Integer postId, String title, String body, Boolean isNotice) {
        PostContent postContent = postContentRepository.findWithPostByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));
        Post post = postContent.getPost();

        Document document = Jsoup.parse(body);
        String bodyText = document.text();
        String summary = bodyText.length() > 90 ? bodyText.substring(0, 90) + "..." : bodyText;
        Element imgElement = document.select("img").first();
        String thumbnail = imgElement != null ? imgElement.attr("src") : null;

        LocalDateTime now = LocalDateTime.now();
        postContent.updateUpdatedAt(now);
        postContent.updateTitle(title);
        postContent.updateBody(body);
        postContent.updateSummary(summary);
        postContent.updateThumbnail(thumbnail);
        post.updatePriority(isNotice ? 1 : 0);
        post.updateCategory(isNotice ? "공지" : "일반");
        post.updateIsNotice(isNotice);
    }

    /*
      게시글 삭제 함수.
     * postId에 해당하는 게시글을 삭제하는 함수임.
     * 게시글이 삭제되는 중에 게시글에 관한 내용을 업데이트 할 수 없음.
     * 게시글에 존재하는 댓글 전체 삭제 후 게시글 삭제.
     * 삭제하고자 하는 게시글이 인기글인 경우, 인기글 개수도 1 감소.
    */
    @Transactional
    public void deletePost(Integer boardId, Integer postId) {
        if (postRepository.updateIsDeleted(postId, true) == 0) {
            throw new Error404Exception("존재하지 않는 게시글입니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        List<Integer> commentIds = commentRepository.findIdsByPostId(postId);
        commentRecommendMemberRepository.deleteByCommentIds(commentIds);
        commentRecommendCountRepository.deleteByCommentIds(commentIds);
        commentRepository.deleteByCommentIds(commentIds);

        postRecommendMemberRepository.deleteByPostId(postId);
        postCommentCountRepository.deleteByPostId(postId);
        postRecommendCountRepository.deleteByPostId(postId);
        postHitCountRepository.deleteByPostId(postId);
        postContentRepository.deleteByPostId(postId);
        postRepository.deleteByPostId(postId);

        Integer dropHotPostCount = post.getIsHot() ? -1 : 0;
        if (boardPostCountRepository.updateByBoardId(boardId, -1, dropHotPostCount) == 0) {
            throw new Error404Exception("존재하지 않는 게시판입니다.");
        }
    }

    /*
      현재 사용자가 [공지] 카테고리의 게시글을 작성할 수 있는지 true/false로 반환하는 함수.
     * 현재 사용자의 Role을 통해서 판단함.
     * role의 value가 2이상인 것은 admint 이상의 role인 경우임.
     * 현재는 role이 user와 admin 밖에 없기때문에 admin만 공지사항을 올릴 수 있음.
    */
    public Boolean hasNoticeAccess(Role role) {
        return role.getValue() >= 2;
    }

    /*
      게시글의 댓글 개수를 반환하는 함수.
     * 게시글의 실제 댓글 수(commentCount)와 총 댓글 수(totalCommentCount)를 반환함.
    */
    @Transactional(readOnly = true)
    public PostCommentCountDto getPostCommentCount(Integer postId) {
        PostCommentCount postCommentCount = postCommentCountRepository.findByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        return PostCommentCountDto.builder()
                .commentCount(postCommentCount.getCommentCount())
                .totalCommentCount(postCommentCount.getTotalCommentCount())
                .build();
    }

    /*
      게시글의 댓글 개수를 반환하는 함수(2).
     * 게시글의 총 댓글 수(totalCommentCount)를 반환함.
    */
    @Transactional(readOnly = true)
    public Integer getTotalCommentCount(Integer postId) {
        PostCommentCount postCommentCount = postCommentCountRepository.findByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        return postCommentCount.getTotalCommentCount();
    }

    /*
      게시글 수정 시 게시글의 이전 내용을 출력하는 함수.
     * 게시글의 이전 내용을 출력함.
    */
    @Transactional(readOnly = true)
    public RewriteDto getRewrite(Integer postId) {
        PostContent postContent = postContentRepository.findWithPostByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));
        Post post = postContent.getPost();

        return RewriteDto.builder()
                .id(post.getId())
                .authorId(post.getMember().getId())
                .title(postContent.getTitle())
                .body(postContent.getBody())
                .isNotice(post.getIsNotice())
                .build();
    }

    /*
      게시글 추천수 증가 함수.
     * 사용자가 이 게시글을 처음 추천하면, 게시글의 추천수를 1 증가시킴.
     * 증가된 추천수를 반환함.
     * 만약 게시글의 추천수가 특정 수를 넘어가면, 게시글의 카테고리는 [인기]로 업데이트 되며, 사용자가 게시글에 추천한 사용자로 기록됨.(PostRecommendMember)
     * 인기글로 업데이트하며, 인기글로 업데이트된 시간을 설정한 뒤, 인기글 수(hotPostCount) 가 1 증가함.
     * 만약 사용자가 이미 추천한 게시글인 경우 isSuccess는 false임.
    */
    @Transactional
    public IncPostRecommendResponseDto incPostRecommend(Integer userId, Integer postId) {
        if (!postRecommendMemberRepository.existsByPostAndMemberId(postId, userId)) {
            if (postRecommendCountRepository.updateByPostId(postId, 1) == 0) {
                throw new Error404Exception("존재하지 않는 게시글입니다.");
            }

            PostRecommendCount postRecommendCount = postRecommendCountRepository.findWithPostByPostId(postId)
                    .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));
            Post post = postRecommendCount.getPost();
            Member member = memberRepository.getReferenceById(userId);

            PostRecommendMember postRecommendMember = PostRecommendMember.builder()
                    .post(post)
                    .member(member)
                    .build();
            postRecommendMemberRepository.save(postRecommendMember);

            if (postRecommendCount.getRecommendCount() >= 1) {
                LocalDateTime now = LocalDateTime.now();
                post.updateIsHot(true);
                post.updateHotRegisteredTime(now);
                if (post.getCategory().equals("일반")) {
                    post.updateCategory("인기");
                }

                if (boardPostCountRepository.updateByBoardId(post.getBoard().getId(), 0, 1) == 0) {
                    throw new Error404Exception("존재하지 않는 게시판입니다.");
                }
            }

            return IncPostRecommendResponseDto.builder()
                    .isSuccess(true)
                    .recommendCount(postRecommendCount.getRecommendCount())
                    .build();
        }
        else {
            return IncPostRecommendResponseDto.builder()
                    .isSuccess(false)
                    .recommendCount(null)
                    .build();
        }
    }

    /*
      게시글 조회수 증가 함수.
     * 게시글의 조회수를 1 증가시킴.
    */
    @Transactional
    public void incPostHit(Integer postId) {
        if (postHitCountRepository.updateByPostId(postId, 1) == 0) {
            throw new Error404Exception("존재하지 않는 게시글입니다.");
        }
    }

    /*
      상위 6개 게시판의 상위 10개 게시글 출력 함수.(생성일 기준)
     * boardId가 1~6인 게시판의 상위 10개 게시글을 출력함.
     * 60개의 게시글을 얻은 다음, boardId가 같은 것끼리 분류하여 List로 만듦.
     * 따라서 10개의 게시글이 존재하는 6개의 List가 반환됨.
    */
    @Transactional(readOnly = true)
    public List<TopBoardDto> getTopBoardList() {
        List<Object[]> results = postRepository.findTopPostList();
        List<Board> boardResult = boardRepository.findTopSixBoard();

        List<TopPostDto> topPostDtoList = results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    String title = (String) result[1];
                    Integer boardId = (Integer) result[2];
                    String boardType = (String) result[3];
                    return TopPostDto.builder()
                            .id(postId)
                            .title(title)
                            .boardId(boardId)
                            .boardType(boardType)
                            .build();
                })
                .collect(Collectors.toList());

        List<TopBoardDto> topBoard = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Integer boardId = i;
            Board board = boardResult.get(boardId);

            List<TopPostDto> filteredList = topPostDtoList.stream()
                    .filter(topPostDto -> topPostDto.getBoardId().equals(boardId + 1))
                    .collect(Collectors.toList());

            topBoard.add(TopBoardDto.builder()
                    .boardAlias(board.getAlias())
                    .boardType(board.getType())
                    .topPostDtoList(filteredList)
                    .build());
        }

        return topBoard;
    }


    /*
      모든 게시판에 대한 상위 20개 게시글 출력 함수.(인기글 등록 시간 기준)
     * 게시판 구분없이 총 20개의 인기글을 출력함.
    */
    @Transactional(readOnly = true)
    public List<TopHotPostDto> getTopHotPostList() {
        List<Object[]> results = postRepository.findTopHotPostList();

        return results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    String title = (String) result[1];
                    String boardType = (String) result[2];

                    return TopHotPostDto.builder()
                            .id(postId)
                            .title(title)
                            .boardType(boardType)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
