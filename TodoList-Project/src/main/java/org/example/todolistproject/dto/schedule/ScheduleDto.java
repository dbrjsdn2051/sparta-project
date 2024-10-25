package org.example.todolistproject.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 10, message = "10글자 이내로 작성해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(max = 500, message = "500자 이내로 작성해주세요.")
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

        private Long scheduleId;

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(max = 500, message = "500자 이내로 작성해주세요.")
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

        private Long scheduleId;

        @NotBlank(message = "패스워드를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResponseOnlySchedule{
        private Long scheduleId;
        private String title;
        private String content;
        private String weather;
        private String username;

        public ResponseOnlySchedule(Schedule schedule) {
            this.scheduleId = schedule.getScheduleId();
            this.title = schedule.getTitle();
            this.content = schedule.getContent();
            this.weather = schedule.getWeather();
            this.username = schedule.getUser().getUsername();
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResponseWithComment {
        private Long scheduleId;
        private String title;
        private String content;
        private String weather;
        private String username;
        private List<CommentDto.Response> comments;

        public ResponseWithComment(Schedule schedule, List<Comment> comments) {
            this.scheduleId = schedule.getScheduleId();
            this.title = schedule.getTitle();
            this.content = schedule.getContent();
            this.weather = schedule.getWeather();
            this.username = schedule.getUser().getUsername();
            this.comments = comments.stream().map(CommentDto.Response::new).toList();
        }
    }
}
