package com.project.web.service;

import com.project.web.controller.dto.post.CommentRequestDto;
import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.Comment;
import com.project.web.domain.Member;
import com.project.web.domain.Post;
import com.project.web.exception.Error403Exception;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.CommentRepository;
import com.project.web.repository.MemberRepository;
import com.project.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void saveComment(Long postId, CommentRequestDto commentRequestDto , Long commentId, PrincipalDetails principalDetails){
        if(principalDetails == null){
            throw new Error403Exception("로그인하세요.");
        }
        Member member = memberRepository.findById(principalDetails.getId())
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        Long parentCommentId = commentId != -1 ? commentId : null;

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .body(commentRequestDto.getBody())
                .parentCommentId(parentCommentId)
                .build();


        commentRepository.save(comment);
    }
}
