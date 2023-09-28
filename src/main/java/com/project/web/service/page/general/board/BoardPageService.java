package com.project.web.service.page.general.board;

import com.project.web.controller.dto.post.PostPreviewResponseDto;
import com.project.web.domain.*;
import com.project.web.repository.PostRepository;
import com.project.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardPageService {
    private final PostRepository postRepository;

    public List<PostPreviewResponseDto> getPostPreviewPageDefault(String type, Integer page) {
        Integer size = 15;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Object[]> results = postRepository.findByPageableAndTypeDefault(pageable, type);
        List<PostPreviewResponseDto> postPreviewResponseDtoList = results.getContent().stream()
                .map((result) -> {
                    Post post = (Post) result[0];
                    Board board = (Board) result[1];
                    MemberProfile memberProfile = (MemberProfile) result[2];
                    PostContent postContent = (PostContent) result[3];
                    PostInfo postInfo = (PostInfo) result[4];

                    return PostPreviewResponseDto.builder()
                            .post(post)
                            .board(board)
                            .memberProfile(memberProfile)
                            .postContent(postContent)
                            .postInfo(postInfo)
                            .build();
                })
                .collect(Collectors.toList());
        return postPreviewResponseDtoList;
    }
}
