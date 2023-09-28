package com.project.web.service;

import com.project.web.exception.Error404Exception;
import com.project.web.repository.MemberLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteService {
    private final PostTypeRepository postTypeRepository;
    private final PostTypeDetailRepository postTypeDetailRepository;
    private final MemberLevelRepository memberLevelRepository;
    public String getPostTypeAlias(String type){
        PostType postType = postTypeRepository.findByType(type)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 페이지입니다."));
        PostTypeDetail postTypeDetail = postTypeDetailRepository.findByPostType_Type(postType.getType());
        return postTypeDetail.getAlias();
    }

    public Boolean hasNotificationAccess(Long id){
        MemberLevel memberLevel = memberLevelRepository.findByMember_Id(id)
                .orElseThrow(() -> new Error404Exception("유저를 찾을 수가 없어요."));

        if(memberLevel.getLevel().getGrade() >= 2){
            return true;
        }
        else{
            return false;
        }
    }
}
