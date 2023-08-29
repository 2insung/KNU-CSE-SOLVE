package com.project.web;

import com.project.web.service.RedisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void Redis_동작_테스트(){
        String email = "test@test.com";
        String code = "aaaaa";

        redisService.setDataExpire(email, code, 60*60L);

        Assertions.assertTrue(redisService.existData("test@test.com"));
        Assertions.assertFalse(redisService.existData("impossible"));
        Assertions.assertEquals(redisService.getData(email), "aaaaa");

    }
}
