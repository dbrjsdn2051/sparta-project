package org.example.todolistproject.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.exception.TokenExpiredException;
import org.example.todolistproject.exception.TokenNotFoundException;
import org.example.todolistproject.repository.UserRepository;
import org.example.todolistproject.security.JwtProvider;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Order(1)
public class JwtAuthenticationFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        if (!StringUtils.hasText(requestURI)) {
            throw new BadRequestException();
        }

        if (requestURI.startsWith("/api/login") || requestURI.startsWith("/api/user")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String tokenFromRequest = jwtProvider.getTokenFromRequest(request);

        if (!StringUtils.hasText(tokenFromRequest)) {
            throw new TokenNotFoundException();
        }

        String token = jwtProvider.substringToken(tokenFromRequest);

        if (!jwtProvider.validateToken(token)) {
            throw new TokenExpiredException();
        }

        Claims info = jwtProvider.getUserInfoFromToken(token);

        userRepository.findByUsername(info.getSubject())
                .orElseThrow(NoResultDataException::new);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
