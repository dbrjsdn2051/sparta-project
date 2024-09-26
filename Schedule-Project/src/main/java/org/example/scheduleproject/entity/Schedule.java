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

    public Schedule(UUID scheduleId, UUID userId, String todoList, LocalDateTime now) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.todoList = todoList;
        this.createdAt = now;
        this.updatedAt = now;

    }

    public void update(String todoList, LocalDateTime now) {
        this.todoList = todoList;
        this.updatedAt = now;
    }
}
