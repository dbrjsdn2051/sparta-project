package org.example.todolistproject.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.entity.Role;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDeleteRequestDto {
    private Long scheduleId;
    private String password;
}
