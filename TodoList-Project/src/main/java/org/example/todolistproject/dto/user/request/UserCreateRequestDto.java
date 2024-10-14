package org.example.todolistproject.dto.user.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Role;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequestDto {

    private String username;
    @Email
    private String email;
    private String password;
    private Role role = Role.USER;

    public UserCreateRequestDto(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
