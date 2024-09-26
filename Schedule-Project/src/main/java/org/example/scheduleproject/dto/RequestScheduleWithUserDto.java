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

    public RequestScheduleWithUserDto(String username, String password, String todoList, String email) {
        this.username = username;
        this.password = password;
        this.todoList = todoList;
        this.email = email;
    }

    public RequestScheduleWithUserDto(String username, String password, String todoList) {
        this.username = username;
        this.password = password;
        this.todoList = todoList;
    }
}
