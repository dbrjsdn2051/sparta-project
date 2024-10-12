package org.example.todolistproject.aop;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.exception.AuthorizationException;
import org.example.todolistproject.exception.TokenNotFoundException;
import org.example.todolistproject.security.JwtProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorityAspect {

    private final HttpServletRequest request;
    private final JwtProvider jwtProvider;

    @Pointcut("@annotation(org.example.todolistproject.aop.CheckAuthority)")
    public void checkAuthorityAnnotation() {
    }

    @Before("checkAuthorityAnnotation()")
    public void beforeAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckAuthority checkAuthority = method.getAnnotation(CheckAuthority.class);
        String tokenKey = checkAuthority.value();

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new TokenNotFoundException();
        }

        String cookieValue = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(tokenKey)) {
                cookieValue = cookie.getValue();
                break;
            }
        }

        if (cookieValue == null) {
            throw new TokenNotFoundException();
        }
        cookieValue = cookieValue.replace("%20", " ");

        log.info("cookieValue = {}", cookieValue);
        String token = jwtProvider.substringToken(cookieValue);
        Role role = jwtProvider.getRoles(token);
        if (role == null || role == Role.USER) {
            throw new AuthorizationException();
        }
    }
}
