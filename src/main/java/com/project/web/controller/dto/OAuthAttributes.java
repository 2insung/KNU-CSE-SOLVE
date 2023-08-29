package com.project.web.controller.dto;

import com.project.web.domain.Authority;
import com.project.web.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String,Object> attributes){
        if(registrationId.equals("google")){
            return ofGoogle(userNameAttributeName, attributes);
        }
        else{
            return null;
        }
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity(){
        String uuid = UUID.randomUUID().toString().substring(0,6);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return Member.builder()
                .username(email)
                .nickname("user_" + uuid)
                .password(bCryptPasswordEncoder.encode("password_" + uuid))
                .role(Authority.ROLE_USER)
                .build();
    }

}
