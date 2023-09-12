package com.project.web.controller.dto.my;

import com.project.web.domain.Member;
import com.project.web.domain.MemberProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class EditMemberProfileRequestDto {
    private Long id;
    private MultipartFile file;
    private String nickname;
    private String description;

    public Member toMember(){
        return Member.builder()
                .id(id)
                .nickname(nickname)
                .build();
    }

    public MemberProfile toMemberProfile(MemberProfile memberProfile, String imageUrl, Member member){
        return MemberProfile.builder()
                .id(memberProfile.getId())
                .member(member)
                .imageUrl(imageUrl)
                .createdAt(memberProfile.getCreatedAt())
                .description(description)
                .build();
    }
}
