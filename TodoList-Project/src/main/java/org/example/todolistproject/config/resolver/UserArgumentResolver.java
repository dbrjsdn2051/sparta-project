package org.example.todolistproject.config.resolver;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.exception.TokenNotFoundException;
import org.example.todolistproject.repository.UserRepository;
import org.example.todolistproject.security.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class) &&
                parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String tokenValue = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(JwtProvider.AUTHORIZATION_HEADER)){
                tokenValue = cookie.getValue();
                break;
            }
        }

        if(tokenValue == null){
            throw new TokenNotFoundException();
        }

        tokenValue = tokenValue.replace("%20", " ");
        String token = jwtProvider.substringToken(tokenValue);
        Claims info = jwtProvider.getUserInfoFromToken(token);
        String username = info.getSubject();

        return userRepository.findByUsername(username).orElseThrow(NoResultDataException::new);
    }
}
