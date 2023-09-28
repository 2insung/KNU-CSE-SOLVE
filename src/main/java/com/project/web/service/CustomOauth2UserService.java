package com.project.web.service;

import com.project.web.controller.dto.auth.OAuthAttributes;
import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.*;
import com.project.web.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final MemberAuthRepository memberAuthRepository;
    private final MemberProfileRepository memberProfileRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        return loadUserByAttributes(attributes);

    }

    public OAuth2User loadUserByAttributes(OAuthAttributes attributes) {
        return memberRepository.findByUsernameWithAuthAndProfile(attributes.getEmail())
                .map(result -> createDefaultOAuth2User(result, attributes))
                .orElseGet(() -> signUpDefaultOAuth2User(attributes));
    }

    public OAuth2User createDefaultOAuth2User(Object[] result, OAuthAttributes attributes) {
        Member member = (Member) result[0];
        MemberAuth memberAuth = (MemberAuth) result[1];
        MemberProfile memberProfile = (MemberProfile) result[2];
        return PrincipalDetails.builder()
                .member(member)
                .memberAuth(memberAuth)
                .memberProfile(memberProfile)
                .attributes(attributes.getAttributes())
                .build();
    }

    public OAuth2User signUpDefaultOAuth2User(OAuthAttributes attributes) {
        String nicknameUUID = UUID.randomUUID().toString().substring(0, 6);
        String passwordUUID = UUID.randomUUID().toString().substring(0, 6);

        Member member = Member.builder()
                .username(attributes.getEmail())
                .level(Level.LEVEL_USER)
                .build();
        MemberAuth memberAuth = MemberAuth.builder()
                .password(passwordEncoder.encode(passwordUUID))
                .role(Authority.ROLE_USER)
                .member(member)
                .build();
        MemberProfile memberProfile = MemberProfile.builder()
                .nickname("user_" + nicknameUUID)
                .profileImage(null)
                .postCount(0)
                .commentCount(0)
                .member(member)
                .build();
        MemberDetail memberDetail = MemberDetail.builder()
                .description(null)
                .grade(null)
                .admissionYear(null)
                .member(member)
                .build();

        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
        memberProfileRepository.save(memberProfile);
        memberDetailRepository.save(memberDetail);

        return PrincipalDetails.builder()
                .member(member)
                .memberAuth(memberAuth)
                .memberProfile(memberProfile)
                .attributes(attributes.getAttributes())
                .build();
    }


}
