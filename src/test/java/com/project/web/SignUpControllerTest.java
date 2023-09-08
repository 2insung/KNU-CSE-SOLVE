package com.project.web;

import com.project.web.controller.dto.signup.SignUpRequestDto;
import com.project.web.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignUpService signUpService;

    @Test
    public void 회원가입_페이지_테스트() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    public void 회원가입_post_요청_테스트() throws Exception {
        SignUpRequestDto requestDto = new SignUpRequestDto();

        when(signUpService.signup(any(SignUpRequestDto.class))).thenReturn(new SignUpResponseDto());

        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .flashAttr("signUpRequestDto", requestDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
