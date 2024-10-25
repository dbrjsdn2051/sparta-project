package org.example.todolistproject.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;


    public void changeContent(String content) {
        this.content = content;
    }

    @Builder
    public Comment(String username, String password, String content, Schedule schedule) {
        this.username = username;
        this.password = password;
        this.content = content;
        this.schedule = schedule;
    }
}
