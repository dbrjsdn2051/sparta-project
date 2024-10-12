package org.example.todolistproject.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.todolistproject.dto.RequestDto;
import org.example.todolistproject.entity.Role;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScheduleUpdateRequestDto implements RequestDto {

    private Long scheduleId;
    private String content;
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
