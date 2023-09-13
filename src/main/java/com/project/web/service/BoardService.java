package com.project.web.service;

import com.project.web.controller.dto.PostPreviewResponseDto;
import com.project.web.domain.PostPreview;
import com.project.web.domain.PostType;
import com.project.web.domain.PostTypeDetail;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.PostPreviewRepository;
import com.project.web.repository.PostTypeDetailRepository;
import com.project.web.repository.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final PostTypeRepository postTypeRepository;
    private final PostTypeDetailRepository postTypeDetailRepository;
    private final PostPreviewRepository postPreviewRepository;
    public PostTypeDetail getPostTypeDetail(String type){
        PostType postType = postTypeRepository.findByType(type)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 페이지입니다."));
        PostTypeDetail postTypeDetail = postTypeDetailRepository.findByPostType_Type(postType.getType());
        return postTypeDetail;
    }

    public List<PostPreviewResponseDto> getPostPreviewPageDefault(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<PostPreview> postPreviewsPage = postPreviewRepository.findAll(pageable);
        List<PostPreviewResponseDto> postPreviewResponseDtoList = postPreviewsPage.getContent().stream()
                .map(postPreview -> new PostPreviewResponseDto(postPreview))
                .collect(Collectors.toList());
        return postPreviewResponseDtoList;
    }


}
