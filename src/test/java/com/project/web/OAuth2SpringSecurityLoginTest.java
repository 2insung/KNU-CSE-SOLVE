package com.project.web;

import com.project.web.controller.auth.dto.OAuthAttributes;
import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.domain.member.Member;
import com.project.web.repository.member.MemberRepository;
import com.project.web.service.auth.CustomOauth2UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OAuth2SpringSecurityLoginTest {
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private CustomOauth2UserService customOauth2UserService;

    @Test
    public void 존재하는_사용자_OAuth_로그인_테스트() throws OAuth2AuthenticationException {
        Map<String, Object> customAttributes = new HashMap<>();
        customAttributes.put("email", "test@example.com");
        OAuthAttributes attributes = OAuthAttributes.of("google", "sub", customAttributes);
        Member mockMember = Member.builder()
                .username("test@example.com")
                .build();
        when(memberRepository.findByUsername(attributes.getEmail())).thenReturn(Optional.of(mockMember));

        OAuth2User result = customOauth2UserService.loadUserByAttributes(attributes);
        assertTrue(result instanceof PrincipalDetails);
        assertEquals("test@example.com", result.getAttribute("email"));
    }

    @Test
    public void 새로운_사용자_OAuth_로그인_테스트() throws OAuth2AuthenticationException{
        Map<String, Object> customAttributes = new HashMap<>();
        customAttributes.put("email", "test@example.com");
        OAuthAttributes attributes = OAuthAttributes.of("google", "sub", customAttributes);

        when(memberRepository.findByUsername(attributes.getEmail())).thenReturn(Optional.empty());

        OAuth2User result = customOauth2UserService.loadUserByAttributes(attributes);
        assertTrue(result instanceof PrincipalDetails);
        assertEquals("test@example.com", result.getAttribute("email"));
        verify(memberRepository,times(1)).save(any(Member.class));
    }


}
