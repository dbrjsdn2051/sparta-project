package org.example.todolistproject.dto.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Schedule;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleDto {

    private Long scheduleId;
    private String title;
    private String content;

    public ScheduleDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        @Size(max = 10, message = "10글자 이내로 작성해주세요.")
        private String title;
        private String content;
        private String password;
        @JsonIgnore
        private String weather;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class Update {
        private Long scheduleId;
        private String content;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete {
        private Long scheduleId;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private Long scheduleId;
        private String title;
        private String content;
        private String weather;
        private String username;
        private List<CommentDto.Response> comments;


        public Response(Schedule schedule) {
            this.scheduleId = schedule.getScheduleId();
            this.title = schedule.getTitle();
            this.content = schedule.getContent();
            this.weather = schedule.getWeather();
            this.username = schedule.getUser().getUsername();
            this.comments = schedule.getComments().stream().map(CommentDto.Response::new).toList();
        }
    }
}
