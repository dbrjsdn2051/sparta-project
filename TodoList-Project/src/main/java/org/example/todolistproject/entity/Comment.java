package org.example.todolistproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    private String username;
    private String password;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    @JsonBackReference
    private Schedule schedule;

    public void addSchedule(Schedule schedule) {
        if(!schedule.getComments().contains(this)){
            schedule.getComments().add(this);
        }
        this.schedule = schedule;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
