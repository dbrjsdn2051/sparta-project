package org.example.todolistproject.service;

import org.assertj.core.api.Assertions;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.login.LoginDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    LoginService loginService;

    LoginDto vaildLoginDto;
    LoginDto invalidPassword;
    LoginDto nonUserLoginDto;
    User user;

    @BeforeEach
    void init() {

        // 유효한 로그인
        vaildLoginDto = new LoginDto("user@naver.com", "1234");

        // 비밀번호가 틀린 로그인
        invalidPassword = new LoginDto("user@naver.com", "wrong");

        // 존재하지 않는 사용자 로그인
        nonUserLoginDto = new LoginDto("notfound@naver.com", "1234");

        user = new User("user", "user@naver.com", "1234", Role.USER);
    }

    @Test
    void loginSuccessTest() {
        // given
        when(userRepository.findByEmail(vaildLoginDto.getEmail())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(vaildLoginDto.getPassword(), user.getPassword())).thenReturn(true);

        // when
        User result = loginService.loadUserByEmail(vaildLoginDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(vaildLoginDto.getEmail());
        assertThat(result.getPassword()).isEqualTo(vaildLoginDto.getPassword());

        verify(userRepository, times(1)).findByEmail(any());
        verify(passwordEncoder, times(1)).matches(vaildLoginDto.getPassword(), user.getPassword());
    }

    @Test
    void loginFailPasswordTest() {

        // given
        when(userRepository.findByEmail(invalidPassword.getEmail())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(invalidPassword.getPassword(), user.getPassword())).thenReturn(false);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> loginService.loadUserByEmail(invalidPassword));

        verify(passwordEncoder, times(1)).matches(any(), any());
    }

    @Test
    void loginFailNotFoundUserTest() {
        //given
        when(userRepository.findByEmail(nonUserLoginDto.getEmail())).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoResultDataException.class, () -> loginService.loadUserByEmail(nonUserLoginDto));
        verify(userRepository, times(1)).findByEmail(any());
    }




}