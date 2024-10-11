package org.example.todolistproject.dto.schedule.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleCreateRequestDto {

    @Size(max = 10, message = "10글자 이내로 작성해주세요.")
    private String title;
    private String content;
    private String password;
    private String weather;
}
