package com.project.web.config;

import com.project.web.handler.CustomLoginFailureHandler;
import com.project.web.service.CustomOauth2UserService;
import com.project.web.service.CustomUserDetailsService;
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
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOauth2UserService customOauth2UserService;

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
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()


                .sessionManagement()
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId)
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()


                .authorizeRequests()
                .antMatchers("/private/**").authenticated()
                .antMatchers("/write").authenticated()
                .anyRequest().permitAll()
                .and()


                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/public/login")
                .loginProcessingUrl("/public/login")
                .defaultSuccessUrl("/")
                .failureHandler(customLoginFailureHandler)
                .and()


                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .alwaysRemember(false)
                .userDetailsService(customUserDetailsService)
                .and()


                .logout()
                .logoutUrl("/private/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("SESSION")
                .and()

                .oauth2Login()
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(customOauth2UserService);


        return http.build();
    }

}