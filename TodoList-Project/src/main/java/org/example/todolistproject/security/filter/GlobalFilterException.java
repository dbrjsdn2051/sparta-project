package org.example.todolistproject.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.todolistproject.exception.TokenExpiredException;
import org.example.todolistproject.exception.TokenNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(0)
public class GlobalFilterException extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(new TokenExpiredException().getMessage());
        } catch (TokenNotFoundException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(new TokenNotFoundException().getMessage());
        }
    }
}
