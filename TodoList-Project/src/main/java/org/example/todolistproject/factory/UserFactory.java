package org.example.todolistproject.factory;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.user.UserDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public User createUser(UserDto.Create dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }


    public void updateUser(UserDto.Update dto, User user) {
        passwordEncoder.validPassword(dto.getPassword(), user.getPassword());
        String email = dto.getEmail();
        String username = dto.getUsername();
        user.change(username, email);
    }

    public void deleteUser(UserDto.Delete dto, User user) {
        passwordEncoder.validPassword(dto.getPassword(), user.getPassword());
    }

    public UserDto.Response getUser(User user) {
        return new UserDto.Response(user);
    }

}
