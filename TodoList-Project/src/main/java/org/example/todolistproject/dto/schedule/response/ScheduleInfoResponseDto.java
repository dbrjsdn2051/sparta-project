package org.example.todolistproject.dto.schedule.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Comment;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInfoResponseDto {

    private Long scheduleId;
    private String title;
    private String content;
    private String weather;
    private String username;
    private List<Comment> comments;
}
