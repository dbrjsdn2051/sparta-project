package org.example.todolistproject.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    private String email;
    private String password;

}
