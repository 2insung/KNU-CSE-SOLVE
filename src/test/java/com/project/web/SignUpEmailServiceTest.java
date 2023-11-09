package com.project.web;

import com.project.web.service.auth.SignUpEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SignUpEmailServiceTest {
    @Autowired
    private SignUpEmailService signUpEmailService;
    @Autowired
    private RedisService redisService;

    @Test
    public void checkCode_테스트_true(){
        String email = "test@test.com";
        String code = "aaaaaa";
        redisService.setDataExpire(email, code, 60*30L);

        Assertions.assertTrue(signUpEmailService.checkCode(email,code));
    }

    @Test
    public void checkCode_테스트_false(){
        String email = "test@test.com";
        String code = "aaaaaa";
        redisService.setDataExpire(email, code, 60*30L);

        Assertions.assertFalse(signUpEmailService.checkCode(email,"bbbbbb"));
    }
}
