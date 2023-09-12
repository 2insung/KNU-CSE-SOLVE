package com.project.web.service;

import com.project.web.controller.dto.my.EditMemberProfileRequestDto;
import com.project.web.domain.Member;
import com.project.web.domain.MemberProfile;
import com.project.web.repository.MemberProfileRepository;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MyService {
    private final S3UploaderService s3UploaderService;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberRepository memberRepository;
    public void editMemberProfile(EditMemberProfileRequestDto requestDto) throws IOException {
        MemberProfile prevMemberProfile = memberProfileRepository.findByMember_Id(requestDto.getId());
        String imageUrl = prevMemberProfile.getImageUrl();
        if(requestDto.getFile() != null && !requestDto.getFile().isEmpty()){
            imageUrl = s3UploaderService.upload(requestDto.getFile(), "images");
        }
        Member member = requestDto.toMember();
        MemberProfile memberProfile = requestDto.toMemberProfile(prevMemberProfile, imageUrl, member);
        memberRepository.save(member);
        memberProfileRepository.save(memberProfile);
    }

    public Boolean checkValidNickname(String nickname){
        if(memberRepository.existsByNickname(nickname)){
            return false;
        }
        else{
            return true;
        }
    }
}
