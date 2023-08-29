package com.project.web;

import com.project.web.service.EmailService;
import com.project.web.service.RedisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.internet.MimeMessage;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisService redisService;

    @Test
    public void checkCode_테스트_true(){
        String email = "test@test.com";
        String code = "aaaaaa";
        redisService.setDataExpire(email, code, 60*30L);

        Assertions.assertTrue(emailService.checkCode(email,code));
    }

    @Test
    public void checkCode_테스트_false(){
        String email = "test@test.com";
        String code = "aaaaaa";
        redisService.setDataExpire(email, code, 60*30L);

        Assertions.assertFalse(emailService.checkCode(email,"bbbbbb"));
    }
}
