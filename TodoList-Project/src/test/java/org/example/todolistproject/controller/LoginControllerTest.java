package org.example.todolistproject.controller;

import org.example.todolistproject.entity.Role;
import org.example.todolistproject.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    private JwtProvider jwtProvider;
    private String validToken;

    @BeforeEach
    public void init(){
        validToken = jwtProvider.createToken("user1", Role.USER);
    }

    @Test
    public void createTokenTest(){
        System.out.println("validToken = " + validToken);
    }
}