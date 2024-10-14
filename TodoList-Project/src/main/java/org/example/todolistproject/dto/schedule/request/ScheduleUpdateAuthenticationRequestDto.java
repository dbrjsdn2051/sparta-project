package org.example.todolistproject.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.todolistproject.dto.AuthenticationRequestDto;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScheduleUpdateAuthenticationRequestDto implements AuthenticationRequestDto {

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
