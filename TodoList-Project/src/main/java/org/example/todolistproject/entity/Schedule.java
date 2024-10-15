package org.example.todolistproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    private String title;
    @JsonIgnore
    private String password;

    private String content;

    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "schedule", orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    public void addUser(User user) {
        if(!user.getSchedules().contains(this)){
            user.getSchedules().add(this);
        }
        this.user = user;
    }

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
