package org.example.scheduleproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestScheduleWithUserDto {

    private String username;
    private String password;
    private String todoList;
    private String email;

}
