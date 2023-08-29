package com.project.web;

import com.project.web.controller.dto.PrincipalDetails;
import com.project.web.domain.Authority;
import com.project.web.domain.Member;
import com.project.web.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpringSecurityLoginTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String TEST_USERNAME = "user";
    private static final String TEST_PASSWORD = "password";

    @BeforeEach
    public void setUp(){
        Member member = Member.builder()
                .username(TEST_USERNAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .role(Authority.ROLE_USER)
                .build();

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        when(customUserDetailsService.loadUserByUsername(TEST_USERNAME))
                .thenReturn(principalDetails);
    }

    @Test
    public void 로그인_성공_테스트() throws Exception{
        mockMvc.perform(formLogin().user(TEST_USERNAME).password(TEST_PASSWORD)
                        .loginProcessingUrl("/login"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    public void 로그인_실패_테스트_1() throws Exception{
        mockMvc.perform(formLogin().user(TEST_USERNAME).password("wrongPassword"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
    @Test
    public void 로그인_실패_테스트_2() throws Exception{
        mockMvc.perform(formLogin().user("wrongUser").password(TEST_PASSWORD))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
    @Test
    public void 로그인_실패_테스트_3() throws Exception{
        mockMvc.perform(formLogin().user("wrongUser").password("wrongPassword"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
