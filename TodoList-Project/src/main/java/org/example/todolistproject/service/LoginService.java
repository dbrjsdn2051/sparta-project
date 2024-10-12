package org.example.todolistproject.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.aop.TableType;
import org.example.todolistproject.aop.ValidPassword;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.login.LoginDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.UserRepository;
import org.example.todolistproject.security.JwtProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void loadUserByEmail(LoginDto dto, HttpServletResponse response) {
        User findUser = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(NoResultDataException::new);

        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new MissMatchPasswordException();
        }

        String token = jwtProvider.createToken(findUser.getUsername(), findUser.getRole());
        jwtProvider.addJwtToCookie(token, response);
    }
}
