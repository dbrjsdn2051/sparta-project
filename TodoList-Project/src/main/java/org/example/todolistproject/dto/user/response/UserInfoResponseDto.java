package org.example.todolistproject.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private List<Schedule> schedules;
}