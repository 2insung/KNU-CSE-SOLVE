package com.project.web.controller.dto;

import com.project.web.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String username;
    private String nickname;

    public static SignUpResponseDto of(Member member){
        return new SignUpResponseDto(member.getUsername(), member.getNickname());
    }
}
