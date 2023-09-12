package com.project.web.service;

import com.project.web.domain.PostType;
import com.project.web.domain.PostTypeDetail;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.PostTypeDetailRepository;
import com.project.web.repository.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final PostTypeRepository postTypeRepository;
    private final PostTypeDetailRepository postTypeDetailRepository;
    public PostTypeDetail getPostTypeDetail(String type){
        PostType postType = postTypeRepository.findByType(type)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 페이지입니다."));
        PostTypeDetail postTypeDetail = postTypeDetailRepository.findByPostType_Type(postType.getType());
        return postTypeDetail;
    }

}
