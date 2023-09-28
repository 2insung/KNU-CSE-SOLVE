package com.project.web.service.page.general.board;

import com.project.web.controller.dto.post.CommentResponseDto;
import com.project.web.controller.dto.post.PostPageResponseDto;
import com.project.web.controller.dto.post.PostPreviewResponseDto;
import com.project.web.domain.*;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.CommentRepository;
import com.project.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostPageService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostPageResponseDto getPostWithTypeAndId(String type, Long postId) {
        Object[] result = postRepository.findByTypeAndId(type, postId)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시글입니다."));

        Post post = (Post) result[0];
        Board board = (Board) result[1];
        MemberProfile memberProfile = (MemberProfile) result[2];
        PostContent postContent = (PostContent) result[3];
        PostInfo postInfo = (PostInfo) result[4];

        return PostPageResponseDto.builder()
                .post(post)
                .board(board)
                .memberProfile(memberProfile)
                .postContent(postContent)
                .postInfo(postInfo)
                .build();
    }

    public List<CommentResponseDto> getCommentWithPageableAndPostId(Integer page, Long postId){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Object[]> results = commentRepository.findByPageableAndPostId(pageable, postId);
        List<CommentResponseDto> commentResponseDtoList = results.getContent().stream()
                .map((result) -> {
                    Comment comment = (Comment) result[0];
                    Post post = (Post) result[1];
                    MemberProfile memberProfile = (MemberProfile) result[2];
                    MemberProfile parentMemberProfile = (MemberProfile) result[3];



                })
                .collect(Collectors.toList());

    }
}
