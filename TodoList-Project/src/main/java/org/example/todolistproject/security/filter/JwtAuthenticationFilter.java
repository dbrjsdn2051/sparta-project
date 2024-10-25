package org.example.todolistproject.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
import org.example.todolistproject.repository.UserRepository;
import org.example.todolistproject.security.JwtProvider;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Order(1)
public class JwtAuthenticationFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        if (!StringUtils.hasText(requestURI)) {
            throw new CustomException(ErrorCode.TOKEN_MISSING);
        }

        if (requestURI.startsWith("/api/login") || requestURI.startsWith("/api/user")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String tokenFromRequest = jwtProvider.getTokenFromRequest(request);

        if (!StringUtils.hasText(tokenFromRequest)) {
            throw new CustomException(ErrorCode.TOKEN_MISSING);
        }

        String token = jwtProvider.substringToken(tokenFromRequest);

        if (!jwtProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        Claims info = jwtProvider.getUserInfoFromToken(token);

        userRepository.findByUsername(info.getSubject())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
