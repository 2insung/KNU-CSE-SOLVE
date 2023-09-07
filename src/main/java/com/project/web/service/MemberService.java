package com.project.web.service;


import com.project.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Boolean checkExistingMemberByNickname(String nickname){
        if(memberRepository.existsByNickname(nickname)){
            return true;
        }
        else{
            return false;
        }
    }

}
