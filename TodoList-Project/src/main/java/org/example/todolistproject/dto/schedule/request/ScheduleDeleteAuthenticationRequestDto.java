package org.example.todolistproject.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.dto.RequestDto;
import org.example.todolistproject.entity.Role;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDeleteRequestDto implements RequestDto {
    private Long scheduleId;
    private String password;

    @Override
    public Long getRequestId() {
        return scheduleId;
    }

    @Override
    public String getRequestPassword() {
        return password;
    }
}
