package com.project.web.service.board;

import com.project.web.controller.community.dto.post.*;
import com.project.web.domain.board.Board;
import com.project.web.domain.comment.Comment;
import com.project.web.domain.member.Level;
import com.project.web.domain.member.Member;
import com.project.web.domain.post.*;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.board.BoardPostCountRepository;
import com.project.web.repository.board.BoardRepository;
import com.project.web.repository.comment.CommentChildCountRepository;
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
    private final CommentChildCountRepository commentChildCountRepository;
    private final CommentRecommendMemberRepository commentRecommendMemberRepository;
    private final int noticePriority = 100000000;

    @Transactional(readOnly = true)
    public List<PostPreviewDto> getPostPreviewListByBoardId(Integer boardId, Integer pageSize, Integer pageNumber) {
        List<Object[]> results = postRepository.findPostPreviewByBoardId(boardId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    Integer authorId = (Integer) result[1];
                    Boolean isNotice = (Boolean) result[2];
                    Boolean isHot = (Boolean) result[3];
                    LocalDateTime createdAt = ((Timestamp) result[4]).toLocalDateTime();
                    String authorNickname = (String) result[5];
                    String authorProfileImage = (String) result[6];
                    String title = (String) result[7];
                    String summary = (String) result[8];
                    String thumbnail = (String) result[9];
                    Integer hitCount = (Integer) result[10];
                    Integer recommendCount = (Integer) result[11];
                    Integer commentCount = (Integer) result[12];

                    return PostPreviewDto.builder()
                            .postId(postId)
                            .authorId(authorId)
                            .isNotice(isNotice)
                            .isHot(isHot)
                            .createdAt(createdAt)
                            .authorNickname(authorNickname)
                            .authorProfileImage(authorProfileImage)
                            .title(title)
                            .summary(summary)
                            .thumbnail(thumbnail)
                            .hitCount(hitCount)
                            .recommendCount(recommendCount)
                            .commentCount(commentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostPreviewDto> getHotPostPreviewListAll(Integer pageSize, Integer pageNumber) {
        List<Object[]> results = postRepository.findHotPostPreviewAll(pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    Integer authorId = (Integer) result[1];
                    Boolean isNotice = (Boolean) result[2];
                    Boolean isHot = (Boolean) result[3];
                    LocalDateTime createdAt = ((Timestamp) result[4]).toLocalDateTime();
                    String authorNickname = (String) result[5];
                    String authorProfileImage = (String) result[6];
                    String title = (String) result[7];
                    String summary = (String) result[8];
                    String thumbnail = (String) result[9];
                    Integer hitCount = (Integer) result[10];
                    Integer recommendCount = (Integer) result[11];
                    Integer commentCount = (Integer) result[12];

                    return PostPreviewDto.builder()
                            .postId(postId)
                            .authorId(authorId)
                            .isNotice(isNotice)
                            .isHot(isHot)
                            .createdAt(createdAt)
                            .authorNickname(authorNickname)
                            .authorProfileImage(authorProfileImage)
                            .title(title)
                            .summary(summary)
                            .thumbnail(thumbnail)
                            .hitCount(hitCount)
                            .recommendCount(recommendCount)
                            .commentCount(commentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostPreviewDto> getHotPostPreviewListByBoardId(Integer boardId, Integer pageSize, Integer pageNumber) {
        List<Object[]> results = postRepository.findHotPostPreviewByBoardId(boardId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    Integer authorId = (Integer) result[1];
                    Boolean isNotice = (Boolean) result[2];
                    Boolean isHot = (Boolean) result[3];
                    LocalDateTime createdAt = ((Timestamp) result[4]).toLocalDateTime();
                    String authorNickname = (String) result[5];
                    String authorProfileImage = (String) result[6];
                    String title = (String) result[7];
                    String summary = (String) result[8];
                    String thumbnail = (String) result[9];
                    Integer hitCount = (Integer) result[10];
                    Integer recommendCount = (Integer) result[11];
                    Integer commentCount = (Integer) result[12];

                    return PostPreviewDto.builder()
                            .postId(postId)
                            .authorId(authorId)
                            .isNotice(isNotice)
                            .isHot(isHot)
                            .createdAt(createdAt)
                            .authorNickname(authorNickname)
                            .authorProfileImage(authorProfileImage)
                            .title(title)
                            .summary(summary)
                            .thumbnail(thumbnail)
                            .hitCount(hitCount)
                            .recommendCount(recommendCount)
                            .commentCount(commentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Integer userId, Integer postId) {
        Object result = postRepository.findPostById(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        Object[] arr = (Object[]) result;
        Integer authorId = (Integer) arr[1];
        Boolean isNotice = (Boolean) arr[2];
        Boolean isHot = (Boolean) arr[3];
        LocalDateTime createdAt = (LocalDateTime) arr[4];
        String category = (String) arr[5];
        String boardType = (String) arr[6];
        String authorNickname = (String) arr[7];
        String authorProfileImage = (String) arr[8];
        String title = (String) arr[9];
        String body = (String) arr[10];
        LocalDateTime updatedAt = (LocalDateTime) arr[11];
        Integer hitCount = (Integer) arr[12];
        Integer recommendCount = (Integer) arr[13];
        Integer commentCount = (Integer) arr[14];
        Integer totalCommentCount = (Integer) arr[15];
        Boolean isMine = userId != null && userId.equals(authorId);

        return PostDto.builder()
                .postId(postId)
                .authorId(authorId)
                .isNotice(isNotice)
                .isHot(isHot)
                .createdAt(createdAt)
                .category(category)
                .boardType(boardType)
                .authorNickname(authorNickname)
                .authorProfileImage(authorProfileImage)
                .title(title)
                .body(body)
                .updatedAt(updatedAt)
                .hitCount(hitCount)
                .recommendCount(recommendCount)
                .commentCount(commentCount)
                .totalCommentCount(totalCommentCount)
                .isMine(isMine)
                .build();
    }

    @Transactional
    public void savePost(Integer userId, Integer boardId, String title, String body,
                         Boolean isNotice) {
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
        String summary = bodyText.length() > 40 ? bodyText.substring(0, 40) + "..." : bodyText;
        Element imgElement = document.select("img").first();
        String thumbnail = imgElement != null ? imgElement.attr("src") : null;
        PostContent postContent = PostContent.builder()
                .post(post)
                .title(title)
                .body(body)
                .summary(summary)
                .thumbnail(thumbnail)
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

    @Transactional
    public void updatePost(Integer postId, String title, String body, Boolean isNotice) {
        PostContent postContent = postContentRepository.findWithPostByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));
        Post post = postContent.getPost();

        Document document = Jsoup.parse(body);
        String bodyText = document.text();
        String summary = bodyText.length() > 40 ? bodyText.substring(0, 40) + "..." : bodyText;
        Element imgElement = document.select("img").first();
        String thumbnail = imgElement != null ? imgElement.attr("src") : null;

        postContent.updateTitle(title);
        postContent.updateBody(body);
        postContent.updateSummary(summary);
        postContent.updateThumbnail(thumbnail);
        if (isNotice != null) {
            post.updatePriority(isNotice ? 1 : 0);
            post.updateCategory(isNotice ? "공지" : "일반");
            post.updateIsNotice(isNotice);
        }
    }

    @Transactional
    public void deletePost(Integer boardId, Integer postId) {
        if (postRepository.updateIsDeleted(postId, true) == 0) {
            throw new Error404Exception("존재하지 않는 게시글입니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        List<Object[]> commentResults = commentRepository.fetchCommentRelationsByPostId(postId);
        List<Integer> commentIds = commentResults.stream()
                .map((commentResult) -> {
                    Comment comment = (Comment) commentResult[0];
                    return comment.getId();
                })
                .collect(Collectors.toList());
        commentRecommendMemberRepository.deleteByCommentIds(commentIds);
        commentRecommendCountRepository.deleteByCommentIds(commentIds);
        commentChildCountRepository.deleteByCommentIds(commentIds);
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

    public Boolean hasNoticeAccess(Level level) {
        return level.getValue() >= 2;
    }

    @Transactional(readOnly = true)
    public PostCommentCountDto getPostCommentCount(Integer postId) {
        PostCommentCount postCommentCount = postCommentCountRepository.findByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        return PostCommentCountDto.builder()
                .commentCount(postCommentCount.getCommentCount())
                .totalCommentCount(postCommentCount.getTotalCommentCount())
                .build();
    }

    @Transactional(readOnly = true)
    public Integer getTotalCommentCount(Integer postId) {
        PostCommentCount postCommentCount = postCommentCountRepository.findByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        return postCommentCount.getTotalCommentCount();
    }

    @Transactional(readOnly = true)
    public RewriteDto getRewrite(Integer postId) {
        PostContent postContent = postContentRepository.findWithPostByPostId(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));
        Post post = postContent.getPost();

        return RewriteDto.builder()
                .postId(post.getId())
                .postAuthorId(post.getMember().getId())
                .title(postContent.getTitle())
                .body(postContent.getBody())
                .isNotice(post.getIsNotice())
                .build();
    }

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

    @Transactional
    public void incPostHit(Integer postId) {
        if (postHitCountRepository.updateByPostId(postId, 1) == 0) {
            throw new Error404Exception("존재하지 않는 게시글입니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<TopPostListDto> getTopPost() {
        List<Object[]> results = postRepository.findTopPostList();
        List<String> boardResult = boardRepository.findTopSixBoard();

        List<TopPostDto> topPostDtoList = results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    String title = (String) result[1];
                    Integer boardId = (Integer) result[2];
                    String boardType = (String) result[3];
                    return TopPostDto.builder()
                            .postId(postId)
                            .title(title)
                            .boardId(boardId)
                            .boardType(boardType)
                            .build();
                })
                .collect(Collectors.toList());
        List<TopPostListDto> topPostListDtoList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Integer number = i;
            String alias = boardResult.get(i);
            List<TopPostDto> filteredList = topPostDtoList.stream()
                    .filter(topPostDto -> topPostDto.getBoardId().equals(number + 1))
                    .collect(Collectors.toList());
            topPostListDtoList.add(TopPostListDto.builder()
                    .alias(alias)
                    .topPostDtoList(filteredList)
                    .build());
        }

        return topPostListDtoList;
    }

    @Transactional(readOnly = true)
    public List<TopHotPostDto> getTopHotPost() {
        List<Object[]> results = postRepository.findTopHotPostList();

        return results.stream()
                .map((result) -> {
                    Integer postId = (Integer) result[0];
                    String title = (String) result[1];
                    String boardType = (String) result[2];

                    return TopHotPostDto.builder()
                            .postId(postId)
                            .title(title)
                            .boardType(boardType)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
