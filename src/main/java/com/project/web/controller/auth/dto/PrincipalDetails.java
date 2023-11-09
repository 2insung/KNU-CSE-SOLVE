package com.project.web.controller.auth.dto;

import com.project.web.domain.member.Authority;
import com.project.web.domain.member.Level;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {
    private Integer userId;
    private String username;
    private String password;
    private String nickname;
    private String profileImage;
    private Authority role;
    private Level level;
    private Map<String, Object> attributes;

    @Builder
    public PrincipalDetails(Integer userId, String username, String password, String nickname,
                            String profileImage, Authority role, Level level, Map<String, Object> attributes) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
        this.level = level;
        this.attributes = attributes;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> role.name());
        return collection;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
