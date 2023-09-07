package com.project.web;

import com.project.web.service.SmtpEmailService;
import com.project.web.service.RedisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SmtpEmailServiceTest {
    @Autowired
    private SmtpEmailService smtpEmailService;
    @Autowired
    private RedisService redisService;

    @Test
    public void checkCode_테스트_true(){
        String email = "test@test.com";
        String code = "aaaaaa";
        redisService.setDataExpire(email, code, 60*30L);

        Assertions.assertTrue(smtpEmailService.checkCode(email,code));
    }

    @Test
    public void checkCode_테스트_false(){
        String email = "test@test.com";
        String code = "aaaaaa";
        redisService.setDataExpire(email, code, 60*30L);

        Assertions.assertFalse(smtpEmailService.checkCode(email,"bbbbbb"));
    }
}
