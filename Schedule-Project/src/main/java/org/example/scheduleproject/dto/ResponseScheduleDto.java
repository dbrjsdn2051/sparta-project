package org.example.scheduleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseScheduleDto {

    private UUID scheduleId;
    private UUID userId;
    private String todoList;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
