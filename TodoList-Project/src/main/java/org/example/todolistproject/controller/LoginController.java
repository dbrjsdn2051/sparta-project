package org.example.todolistproject.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.login.LoginDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.security.JwtProvider;
import org.example.todolistproject.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        User user = loginService.loadUserByEmail(loginDto);

        String token = jwtProvider.createToken(user.getUsername(), user.getRole());
        jwtProvider.addJwtToCookie(token, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
