package com.project.web;

import com.project.web.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailControllerTest {
    @MockBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void email_get_테스트() throws Exception {
        String email = "test@test.com";
        String code = "aaaaaa";
        when(emailService.sendSimpleMessage(email)).thenReturn(code);
        mockMvc.perform(get("/email")
                        .param("username", email))
                .andExpect(status().isOk())
                .andExpect(content().string(code));
    }

    @Test
    public void checkCode_Post_테스트_true() throws Exception {
        String email = "test@test.com";
        String code = "aaaaaa";
        when(emailService.checkCode(email, code)).thenReturn(true);

        mockMvc.perform(post("/checkCode")
                        .param("email", email)
                        .param("code", code)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void checkCode_Post_테스트_false() throws Exception {
        String email = "test@test.com";
        String code = "aaaaaa";
        when(emailService.checkCode(email, code)).thenReturn(false);

        mockMvc.perform(post("/checkCode")
                        .param("email", email)
                        .param("code", code)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

}
