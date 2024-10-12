package org.example.todolistproject.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.user.request.UserCreateRequestDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetails {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddlAuto;

    @PostConstruct
    public void createAdmin() {
        if (!ddlAuto.equals("create")) return;

        String encode = passwordEncoder.encode("1234");
        UserCreateRequestDto admin =
                new UserCreateRequestDto("admin", "admin@naver.com", encode, Role.ADMIN);

        User user = modelMapper.map(admin, User.class);
        userRepository.save(user);
    }
}
