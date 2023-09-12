package com.project.web.controller.dto.auth;

import com.project.web.domain.Member;
import com.project.web.domain.MemberAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private MemberAuth memberAuth;
    private Map<String, Object> attributes;

    public PrincipalDetails(MemberAuth memberAuth){
        this.memberAuth = memberAuth;
    }

    public PrincipalDetails(MemberAuth memberAuth, Map<String, Object> attributes){
        this.memberAuth = memberAuth;
        this.attributes = attributes;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities(){
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
    public String getPassword(){
        return memberAuth.getPassword();
    }

    @Override
    public String getUsername(){
        return memberAuth.getUsername();
    }

    public Long getId(){
        return memberAuth.getMember().getId();
    }

    public String getNickname(){
        return memberAuth.getMember().getNickname();
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public Map<String,Object> getAttributes(){
        return attributes;
    }

    @Override
    public String getName(){
        return null;
    }
}
