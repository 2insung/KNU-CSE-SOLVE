package com.project.web.service;

import com.project.web.controller.dto.auth.OAuthAttributes;
import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.domain.Member;
import com.project.web.repository.MemberRepository;
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
    private MemberRepository memberRepository;

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
        return memberRepository.findByUsername(attributes.getEmail())
                .map(member -> createDefaultOAuth2User(member, attributes))
                .orElseGet(() -> signUpDefaultOAuth2User(attributes));
    }

    public OAuth2User createDefaultOAuth2User(Member member, OAuthAttributes attributes) {
        return new PrincipalDetails(member, attributes.getAttributes());
    }

    public OAuth2User signUpDefaultOAuth2User(OAuthAttributes attributes) {
        Member member = attributes.toEntity();
        memberRepository.save(member);
        return new PrincipalDetails(member, attributes.getAttributes());
    }


}
