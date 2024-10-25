package org.example.todolistproject.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.User;

public class UserDto {


    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {

        @NotBlank(message = "이름은 필수로 입력해주세요.")
        @Size(max = 10, message = "이름은 최대 10글자입니다.")
        private String username;

        @NotBlank(message = "이메일은 필수로 입력해주세요.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private String email;

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        private String password;
        private Role role = Role.USER;

        public Create(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {

        private Long userId;

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 10, message = "이름은 최대 10글자입니다.")
        private String username;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식으로 입력해주세요.")
        private String email;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        private String password;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete  {

        private Long userId;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
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

        public Response(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.role = user.getRole();
        }
    }
}
