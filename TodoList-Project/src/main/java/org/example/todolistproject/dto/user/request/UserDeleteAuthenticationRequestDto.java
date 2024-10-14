package org.example.todolistproject.dto.user.request;

import lombok.Getter;
import lombok.Setter;
import org.example.todolistproject.dto.AuthenticationRequestDto;

@Getter
@Setter
public class UserDeleteAuthenticationRequestDto implements AuthenticationRequestDto {

    private Long userId;
    private String password;

    @Override
    public Long getRequestId() {
        return userId;
    }

    @Override
    public String getRequestPassword() {
        return password;
    }
}
