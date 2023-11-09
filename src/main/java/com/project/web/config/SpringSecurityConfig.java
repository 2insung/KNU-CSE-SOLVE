package com.project.web.config;

import com.project.web.handler.CustomLoginFailureHandler;
import com.project.web.service.auth.CustomOauth2UserService;
import com.project.web.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                .and()


                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()


                .sessionManagement()
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId)
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()


                .authorizeRequests()
                .anyRequest().permitAll()
                .and()


                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureHandler(customLoginFailureHandler)
                .and()


                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .alwaysRemember(false)
                .and()


                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("SESSION")
                .and()

                .oauth2Login()
                .defaultSuccessUrl("/")
                .userInfoEndpoint();


        return http.build();
    }

}
