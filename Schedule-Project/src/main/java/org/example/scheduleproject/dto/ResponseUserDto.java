package org.example.scheduleproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDto {

    private UUID scheduleId;
    private UUID userId;
    private String username;
    private String todoList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
