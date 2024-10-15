package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.login.LoginDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User loadUserByEmail(LoginDto dto) {
        User findUser = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(NoResultDataException::new);

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new MissMatchPasswordException();
        }

        return findUser;
    }
}
