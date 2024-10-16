package org.example.todolistproject.service;

import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.factory.UserFactory;
import org.example.todolistproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserFactory userFactory;

    @InjectMocks
    UserService userService;

    UserDto.Create createDto;
    UserDto.Update updateDto;
    UserDto.Delete deleteDto;
    User user;

    @BeforeEach
    void init(){

    }



}