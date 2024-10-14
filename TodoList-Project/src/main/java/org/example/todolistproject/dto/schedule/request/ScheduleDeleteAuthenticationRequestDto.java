package org.example.todolistproject.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todolistproject.dto.AuthenticationRequestDto;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDeleteAuthenticationRequestDto implements AuthenticationRequestDto {
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
