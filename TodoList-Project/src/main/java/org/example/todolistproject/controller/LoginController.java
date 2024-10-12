package org.example.todolistproject.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.login.LoginDto;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto, HttpServletResponse response)  {
        loginService.loadUserByEmail(loginDto, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
