package com.project.web.service.auth;

import com.project.web.controller.auth.dto.OAuthAttributes;
import com.project.web.controller.auth.dto.PrincipalDetails;
import com.project.web.domain.member.*;
import com.project.web.repository.member.*;
import lombok.RequiredArgsConstructor;
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
    private final MemberPasswordRepository memberPasswordRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final MemberPostCountRepository memberPostCountRepository;
    private final MemberCommentCountRepository memberCommentCountRepository;
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
        return memberRepository.findByUsernameForLogin(attributes.getEmail())
                .map(result -> createDefaultOAuth2User(result, attributes))
                .orElseGet(() -> signUpDefaultOAuth2User(attributes));
    }

    public OAuth2User createDefaultOAuth2User(Object result, OAuthAttributes attributes) {
        Object[] arr = (Object[]) result;
        Integer memberId = (Integer) arr[0];
        String username = (String) arr[1];
        String password = (String) arr[2];
        String nickname = (String) arr[3];
        String profileImage = (String) arr[4];
        Authority role = (Authority) arr[5];
        Level level = (Level) arr[6];

        return PrincipalDetails.builder()
                .userId(memberId)
                .username(username)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .role(role)
                .level(level)
                .build();
    }

    public OAuth2User signUpDefaultOAuth2User(OAuthAttributes attributes) {
        String passwordUUID = UUID.randomUUID().toString();

        Member member = Member.builder()
                .isDeleted(false)
                .build();

        MemberAuth memberAuth = MemberAuth.builder()
                .member(member)
                .username(attributes.getEmail())
                .level(Level.LEVEL_USER)
                .role(Authority.ROLE_USER)
                .build();

        MemberPassword memberPassword = MemberPassword.builder()
                .member(member)
                .password(passwordEncoder.encode(passwordUUID))
                .build();

        MemberDetail memberDetail = MemberDetail.builder()
                .member(member)
                .nickname("user_" + attributes.getEmail())
                .profileImage(null)
                .description(null)
                .grade(null)
                .admissionYear(null)
                .department(null)
                .build();

        MemberPostCount memberPostCount = MemberPostCount.builder()
                .member(member)
                .postCount(0)
                .build();

        MemberCommentCount memberCommentCount = MemberCommentCount.builder()
                .member(member)
                .commentCount(0)
                .build();

        memberRepository.save(member);
        memberAuthRepository.save(memberAuth);
        memberPasswordRepository.save(memberPassword);
        memberDetailRepository.save(memberDetail);
        memberPostCountRepository.save(memberPostCount);
        memberCommentCountRepository.save(memberCommentCount);

        return PrincipalDetails.builder()
                .userId(member.getId())
                .username(memberAuth.getUsername())
                .password(memberPassword.getPassword())
                .nickname(memberDetail.getNickname())
                .profileImage(memberDetail.getProfileImage())
                .role(memberAuth.getRole())
                .level(memberAuth.getLevel())
                .build();
    }

}
