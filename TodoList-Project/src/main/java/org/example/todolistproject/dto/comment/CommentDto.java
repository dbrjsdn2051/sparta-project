package org.example.todolistproject.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Comment;

public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        private String username;

        @NotBlank(message = "댓글을 입력해주세요.")
        @Size(max = 100, message = "댓글 내용은 100자 이내로 작성해주세요.")
        private String content;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {
        private Long commentId;

        @NotBlank(message = "댓글을 입력해주세요.")
        @Size(max = 100, message = "댓글 내용은 100자 이내로 작성해주세요.")
        private String content;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete {
        private Long commentId;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private String username;
        private String content;

        public Response(Comment comment) {
            this.username = comment.getUsername();
            this.content = comment.getContent();
        }
    }


}
