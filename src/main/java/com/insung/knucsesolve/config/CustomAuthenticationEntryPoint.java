package com.insung.knucsesolve.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Spring security 권한 확인 시 401에러 발생 핸들러
// Spring security 권한 확인 시 401에러가 발생한 경우에 /login 으로 redirect함.
// ajax 요청 시 헤더에 /login url을 입력하여 자바스크립트에서 href를 통해 redirect함.
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Redirect-URL", "/login?authentication=false");
        }
        else {
            response.sendRedirect("/login?authentication=false");
        }
    }
}
