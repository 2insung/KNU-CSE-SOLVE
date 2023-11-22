package com.project.web.controller.auth.dto;

import com.project.web.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

// 사용자(세션) 정보
// 권한 체크 및 CRUD 연산 시 사용. (Controller, RestController)
// 로그인하지 않은 경우, null임.
@Getter
public class PrincipalDetails implements UserDetails {
    private final Integer userId;
    private final String username;
    private final String password;
    private final Role role;

    @Builder
    public PrincipalDetails(Integer userId, String username, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
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
}
