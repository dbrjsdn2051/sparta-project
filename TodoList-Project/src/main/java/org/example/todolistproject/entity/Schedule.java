package org.example.todolistproject.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public void changeContent(String content){
        this.content = content;
    }

    @Builder
    public Schedule(String title, String password, String content, String weather, User user) {
        this.title = title;
        this.password = password;
        this.content = content;
        this.weather = weather;
        this.user = user;
    }
}
