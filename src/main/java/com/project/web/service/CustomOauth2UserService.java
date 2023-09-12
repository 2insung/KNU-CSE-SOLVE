package com.project.web.service;

import com.project.web.controller.dto.auth.OAuthAttributes;
import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.*;
import com.project.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private MemberAuthRepository memberAuthRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberProfileRepository memberProfileRepository;
    @Autowired
    private MemberLevelRepository memberLevelRepository;
    @Autowired
    private LevelRepository levelRepository;

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
        return memberAuthRepository.findByUsername(attributes.getEmail())
                .map(memberAuth -> createDefaultOAuth2User(memberAuth, attributes))
                .orElseGet(() -> signUpDefaultOAuth2User(attributes));
    }

    public OAuth2User createDefaultOAuth2User(MemberAuth memberAuth, OAuthAttributes attributes) {
        return new PrincipalDetails(memberAuth, attributes.getAttributes());
    }

    public OAuth2User signUpDefaultOAuth2User(OAuthAttributes attributes) {
        Member member = attributes.toMember();
        MemberAuth memberAuth = attributes.toMemberAuth(member);
        Level level = levelRepository.findByName("유저");
        MemberLevel memberLevel = MemberLevel.builder()
                .level(level)
                .member(member)
                .build();
        MemberProfile memberProfile = MemberProfile.builder()
                .member(member)
                .build();
        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
        memberLevelRepository.save(memberLevel);
        memberProfileRepository.save(memberProfile);
        return new PrincipalDetails(memberAuth, attributes.getAttributes());
    }


}
