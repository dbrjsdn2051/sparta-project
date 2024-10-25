package org.example.todolistproject.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    @PostConstruct
    public void init() {
        byte[] decode = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(decode);
    }


    public String createToken(String username, Role role) {
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public void addJwtToCookie(String token, HttpServletResponse response) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
        }
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.info("Not Found Token");
        throw new CustomException(ErrorCode.TOKEN_MISSING);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명입니다. ");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰입니다. ");
            log.info("JWT claims is empty, 잘못된 JWT 토큰입니다. ");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }

                }
            }
        }
        return null;
    }

    public Role getRoles(String token) {
        Claims info = getUserInfoFromToken(token);
        String role = (String) info.get(AUTHORIZATION_KEY);
        return role.equals("USER") ? Role.USER : Role.ADMIN;
    }

    public String getTokenValueFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JwtProvider.AUTHORIZATION_HEADER)) {
                String cookieValue = cookie.getValue();
                return cookieValue.replace("%20", " ");
            }
        }
        throw new CustomException(ErrorCode.TOKEN_MISSING);
    }

    public Claims getClaimInfoFromCookie(Cookie[] cookies) {
        String tokenValue = getTokenValueFromCookie(cookies);
        String token = substringToken(tokenValue);
        return getUserInfoFromToken(token);
    }

}
