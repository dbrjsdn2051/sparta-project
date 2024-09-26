package org.example.scheduleproject.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class Schedule {

    private UUID scheduleId;
    private UUID userId;
    private String todoList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(UUID scheduleId, UUID userId, String todoList, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.todoList = todoList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
