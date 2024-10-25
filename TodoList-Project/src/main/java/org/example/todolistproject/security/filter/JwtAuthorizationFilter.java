package org.example.todolistproject.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
import org.example.todolistproject.security.JwtProvider;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
@Order(2)
public class JwtAuthorizationFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        if (request.getMethod().equals("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!requestURI.equals("/api/post")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = request.getCookies();

        String tokenValue = jwtProvider.getTokenValueFromCookie(cookies);

        log.info("cookieValue = {}", tokenValue);
        String token = jwtProvider.substringToken(tokenValue);
        Role role = jwtProvider.getRoles(token);
        log.info("{}", role);
        if (role == null || role == Role.USER) {
            throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
        }
    }
}
