package org.example.todolistproject.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequestDto {

    private Long userId;
    private String password;
}
