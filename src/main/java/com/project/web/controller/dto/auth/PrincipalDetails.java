package com.project.web.controller.dto.auth;

import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import com.project.web.domain.MemberProfile;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private Member member;
    private MemberAuth memberAuth;
    private MemberProfile memberProfile;
    private Map<String, Object> attributes;


    @Builder
    public PrincipalDetails(Member member, MemberAuth memberAuth, MemberProfile memberProfile, Map<String, Object> attributes) {
        this.member = member;
        this.memberAuth = memberAuth;
        this.memberProfile = memberProfile;
        this.attributes = attributes;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberAuth.getRole().name();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return memberAuth.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public Long getId() {
        return member.getId();
    }

    public String getNickname() {
        return memberProfile.getNickname();
    }

    public String getProfileImage() {
        return memberProfile.getProfileImage();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
