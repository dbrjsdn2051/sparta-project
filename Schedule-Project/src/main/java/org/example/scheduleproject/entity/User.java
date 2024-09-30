package org.example.scheduleproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class User {

    private UUID userId;
    private UUID scheduleId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(UUID userId, UUID scheduleId, String username, String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}