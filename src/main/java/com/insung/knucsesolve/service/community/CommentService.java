package com.insung.knucsesolve.service.community;

import com.insung.knucsesolve.controller.community.dto.comment.view.TopCommentDto;
import com.insung.knucsesolve.domain.comment.CommentRecommendMember;
import com.insung.knucsesolve.repository.comment.CommentRecommendMemberRepository;
import com.insung.knucsesolve.controller.community.dto.comment.view.CommentDto;
import com.insung.knucsesolve.controller.community.dto.comment.rest.IncCommentRecommendResponseDto;
import com.insung.knucsesolve.domain.comment.Comment;
import com.insung.knucsesolve.domain.comment.CommentStat;
import com.insung.knucsesolve.domain.member.Member;
import com.insung.knucsesolve.domain.post.Post;
import com.insung.knucsesolve.exception.Error500Exception;
import com.insung.knucsesolve.repository.comment.CommentStatRepository;
import com.insung.knucsesolve.repository.comment.CommentRepository;
import com.insung.knucsesolve.repository.member.MemberRepository;
import com.insung.knucsesolve.repository.post.PostRepository;
import com.insung.knucsesolve.repository.post.PostStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostStatRepository postStatRepository;
    private final CommentRepository commentRepository;
    private final CommentStatRepository commentStatRepository;
    private final CommentRecommendMemberRepository commentRecommendMemberRepository;

    /*
      댓글 출력 함수.
     * postId에 해당하는 게시글의 댓글을 pageSize 개수만큼 출력함.
     * pageNumber는 댓글 페이지 번호임.
     * 댓글 생성일의 오름차순으로 출력함. 가장 먼저 저장된 댓글은 1페이지의 첫 댓글이며, 가장 마지막에 저장된 댓글은 마지막 페이지의 마지막 댓글임.
    */
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentDtos(Integer userId, Integer postId, Integer pageSize, Integer pageNumber) {
        List<Object[]> results = commentRepository.findCommentDtosByPostId(postId, pageSize, pageSize * (pageNumber - 1));

        return results.stream()
                .map((result) -> {
                    Integer resultCommentId = (Integer) result[0];
                    Integer resultAuthorId = (Integer) result[1];
                    Integer resultPostId = (Integer) result[2];
                    Integer resultParentAuthorId = (Integer) result[3];
                    Boolean resultIsPostAuthor = (Boolean) result[4];
                    Boolean resultIsRoot = (Boolean) result[5];
                    Boolean resultIsRootChild = (Boolean) result[6];
                    Boolean resultIsDeleted = (Boolean) result[7];
                    String resultBody = (String) result[8];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[9]).toLocalDateTime();
                    Integer resultRecommendCount = (Integer) result[10];
                    String resultAuthorNickname = (String) result[11];
                    String resultAuthorProfileImage = (String) result[12];
                    String resultParentAuthorNickname = (String) result[13];
                    Boolean isMine = userId != null && userId.equals(resultAuthorId);

                    return CommentDto.builder()
                            .id(resultCommentId)
                            .authorId(resultAuthorId)
                            .postId(resultPostId)
                            .parentAuthorId(resultParentAuthorId)
                            .isPostAuthor(resultIsPostAuthor)
                            .isRoot(resultIsRoot)
                            .isRootChild(resultIsRootChild)
                            .isDeleted(resultIsDeleted)
                            .body(resultBody)
                            .createdAt(resultCreatedAt)
                            .recommendCount(resultRecommendCount)
                            .authorNickname(resultAuthorNickname)
                            .authorProfileImage(resultAuthorProfileImage)
                            .parentAuthorNickname(resultParentAuthorNickname)
                            .isMine(isMine)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TopCommentDto> getTopCommentDtos(Integer count) {
        List<Object[]> results = commentRepository.findTopCommentDtos(count);

        return results.stream().
                map((result) -> {
                    Integer resultPostId = (Integer) result[0];
                    Boolean resultIsDeleted = (Boolean) result[1];
                    String resultBody = (String) result[2];
                    LocalDateTime resultCreatedAt = ((Timestamp) result[3]).toLocalDateTime();
                    Integer resultBoardId = (Integer) result[4];
                    String resultBoardAlias = (String) result[5];
                    String resultPosttitle = (String) result[6];

                    return TopCommentDto.builder()
                            .postId(resultPostId)
                            .isDeleted(resultIsDeleted)
                            .body(resultBody)
                            .createdAt(resultCreatedAt)
                            .boardId(resultBoardId)
                            .boardAlias(resultBoardAlias)
                            .postTitle(resultPosttitle)
                            .build();
                }).collect(Collectors.toList());
    }

    /*
     댓글 등록 함수.
     * 입력하는 댓글이 대댓글이 아닌 경우, parentCommentId가 null임.
     * 입력하는 댓글이 대댓글인 경우, parentCommentId가 null이 아님. 루트 댓글의 Child Count를 1 증가.
     * Comment 와 관련된 엔티티들을 모두 save.
     * 현재 Post의 댓글 수와 현재 댓글 작성자의 댓글 수를 1 증가.
    */
    @Transactional
    public void saveComment(Integer userId, Integer postId, Integer parentCommentId, String commentBody) {
        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new Error500Exception("존재하지 않는 댓글입니다."));
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Error500Exception("존재하지 않는 게시글입니다."));
        Member commentAuthor = memberRepository.getReferenceById(userId);

        Comment comment = Comment.builder()
                .member(commentAuthor)
                .post(post)
                .parentMember(parentComment != null ? parentComment.getMember() : commentAuthor)
                .rootCommentId(parentComment != null ? parentComment.getRootCommentId() : null)
                .isPostAuthor(userId.equals(post.getMember().getId()))
                .isRoot(parentComment == null)
                .isRootChild(parentComment != null ? parentComment.getIsRoot() : false)
                .isDeleted(false)
                .body(commentBody)
                .groupCreatedAt(parentComment != null ? parentComment.getGroupCreatedAt() : null)
                .childCount(0)
                .build();
        commentRepository.save(comment);

        CommentStat commentStat = CommentStat.builder()
                .comment(comment)
                .recommendCount(0)
                .build();
        commentStatRepository.save(commentStat);

        if (postStatRepository.updateCommentCountByPostId(postId, 1, 1) == 0) {
            throw new Error500Exception("존재하지 않는 게시글입니다.");
        }

        if (parentComment != null) {
            if (commentRepository.updateChildCountById(parentComment.getRootCommentId(), 1) == 0) {
                throw new Error500Exception("존재하지 않는 댓글입니다.");
            }
        }
    }

    /*
     댓글 삭제 함수.
     * 삭제하고자 하는 댓글의 isDeleted 속성을 true로 변경.
     * updateIsDeleted 에 사용된 쿼리에서 성공한 행이 0개인 경우는, 존재하지 않는 댓글이거나 이미 isDeleted가 처리된 댓글.
     * 삭제할 댓글의 내용을 deleted_comment 테이블에 저장.
     * 삭제하고자 하는 댓글의 child count가 0인 경우는, 댓글이 루트 댓글이지만 자식 댓글이 없는 경우이거나 자식 댓글인 경우.
     * 만약 자식 댓글을 삭제할 경우 루트 댓글의 child count를 1 감소.
     * 자식 댓글을 삭제하는데 루트 댓글이 isDeleted가 true인 상태이고 child count가 0이라면, 삭제할 댓글 리스트에 포함.
     * 현재 Post의 댓글 수와 현재 댓글 작성자의 댓글 수를 1 감소.
     * 반환값은 댓글 등록 후의 게시글 댓글 개수임.
    */
    @Transactional
    public void deleteComment(Integer postId, Integer commentId) {
        if (commentRepository.updateIsDeletedById(commentId, true) == 0) {
            throw new Error500Exception("존재하지 않는 댓글입니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Error500Exception("존재하지 않는 댓글입니다."));

        Integer dropCommentCount = 0;
        Integer dropTotalCommentCount = 0;

        if (comment.getChildCount() == 0) {
            List<Integer> deleteCommentIds = new ArrayList<>();
            deleteCommentIds.add(comment.getId());

            if (!comment.getIsRoot()) {
                if (commentRepository.updateChildCountById(comment.getRootCommentId(), -1) == 0) {
                    throw new Error500Exception("존재하지 않는 댓글입니다.");
                }

                Comment rootComment = commentRepository.findById(comment.getRootCommentId())
                        .orElseThrow(() -> new Error500Exception("존재하지 않는 댓글입니다."));

                if (rootComment.getChildCount() == 0 && rootComment.getIsDeleted()) {
                    deleteCommentIds.add(rootComment.getId());
                }
            }

            if (deleteCommentIds.size() == 1) {
                dropCommentCount = -1;
                dropTotalCommentCount = -1;
            }
            else if (deleteCommentIds.size() == 2) {
                dropCommentCount = -1;
                dropTotalCommentCount = -2;
            }

            commentRecommendMemberRepository.deleteByCommentIds(deleteCommentIds);
            commentStatRepository.deleteByCommentIds(deleteCommentIds);
            commentRepository.deleteByIds(deleteCommentIds);
        }
        else {
            dropCommentCount = -1;
            dropTotalCommentCount = 0;
        }

        if (postStatRepository.updateCommentCountByPostId(postId, dropCommentCount, dropTotalCommentCount) == 0) {
            throw new Error500Exception("존재하지 않는 게시글입니다.");
        }
    }

    /*
      댓글 추천수 증가 함수.
     * 사용자가 이 댓글을 처음 추천하면, 댓글의 추천수를 1 증가시킴.
     * 증가된 추천수를 반환함.
     * 사용자가 댓글에 추천한 사용자로 기록됨.(CommentRecommendMember)
     * 만약 사용자가 이미 추천한 댓글인 경우 isSuccess는 false임.
    */
    @Transactional
    public IncCommentRecommendResponseDto incCommentRecommend(Integer userId, Integer commentId) {
        Member member = memberRepository.getReferenceById(userId);
        Comment comment = commentRepository.getReferenceById(commentId);

        if (!commentRecommendMemberRepository.existsByCommentAndMemberId(commentId, userId)) {
            if (commentStatRepository.updateByCommentId(commentId, 1) == 0) {
                throw new Error500Exception("존재하지 않는 댓글입니다.");
            }

            CommentStat commentStat = commentStatRepository.findById(commentId)
                    .orElseThrow(() -> new Error500Exception("존재하지 않는 댓글입니다."));

            CommentRecommendMember commentRecommendMember = CommentRecommendMember.builder()
                    .comment(comment)
                    .member(member)
                    .build();
            commentRecommendMemberRepository.save(commentRecommendMember);

            return IncCommentRecommendResponseDto.builder()
                    .isSuccess(true)
                    .recommendCount(commentStat.getRecommendCount())
                    .build();
        }
        else {
            return IncCommentRecommendResponseDto.builder()
                    .isSuccess(false)
                    .recommendCount(null)
                    .build();
        }
    }
}
