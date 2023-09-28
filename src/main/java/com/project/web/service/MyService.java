package com.project.web.service;

import com.project.web.controller.dto.my.MyEditFormRequestDto;
import com.project.web.controller.dto.my.MyEditPageResponseDto;
import com.project.web.controller.dto.my.MyPageResponseDto;
import com.project.web.domain.Member;
import com.project.web.domain.MemberDetail;
import com.project.web.domain.MemberProfile;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.MemberDetailRepository;
import com.project.web.repository.MemberProfileRepository;
import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MyService {
    private final S3UploaderService s3UploaderService;
    private final MemberRepository memberRepository;

    @Transactional
    public void editMemberProfile(String prevNickname, MyEditFormRequestDto requestDto) throws IOException {
        Object[] result = memberRepository.findByNicknameWithProfileAndDetail(prevNickname)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 사용자입니다."));

        MemberProfile memberProfile = (MemberProfile) result[1];
        MemberDetail memberDetail = (MemberDetail) result[2];

        memberProfile.updateNickname(requestDto.getNickname());

        if (requestDto.getFile() != null && !requestDto.getFile().isEmpty()) {
            String imageUrl = s3UploaderService.upload(requestDto.getFile(), "images");
            memberProfile.updateProfileImage(imageUrl);
        }

        memberDetail.updateGrade(requestDto.getGrade());
        memberDetail.updateDescription(requestDto.getDescription());
        memberDetail.updateAdmissionYear(requestDto.getAdmissionYear());
        memberDetail.updateDepartment(requestDto.getDepartment());
    }

}
