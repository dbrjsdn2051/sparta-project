package org.example.todolistproject.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleCreateRequestDto {

    private String title;
    private String content;
    private String password;

}
