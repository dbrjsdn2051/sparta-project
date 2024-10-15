package org.example.todolistproject.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;

import java.util.List;

public class UserDto {


    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {
        private String username;
        @Email
        private String email;
        private String password;
        private Role role = Role.USER;

        public Create(String username, String email, String password, Role role) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.role = role;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {
        private Long userId;
        private String username;
        @Email
        private String email;
        private String password;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete  {
        private Long userId;
        private String password;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private Long userId;
        private String username;
        private String email;
        private Role role;
        private List<Schedule> schedules;

        public Response(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.schedules = user.getSchedules();
        }
    }


}
