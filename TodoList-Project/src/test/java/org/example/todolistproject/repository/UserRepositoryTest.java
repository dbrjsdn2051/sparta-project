package org.example.todolistproject.repository;

import org.assertj.core.api.Assertions;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init(){
        User user = new User("user1", "asdf@naver.com", "1234", Role.USER);
        userRepository.save(user);
    }

    @Test
    public void addTest(){
        User findUser = userRepository.findById(1L).orElseThrow();
        assertThat(findUser.getUsername()).isEqualTo("user1");
    }

    @Test
    public void deleteTest(){
        userRepository.deleteById(1L);
        assertThrows(RuntimeException.class, () -> userRepository.findById(1L).orElseThrow());
    }


}