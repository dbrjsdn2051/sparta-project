package org.example.todolistproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    private String username;
    private String password;

    @Setter
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    @JsonBackReference
    private Schedule schedule;

    public void addSchedule(Schedule schedule) {
        schedule.getComments().add(this);
        this.schedule = schedule;
    }

    public Comment(String username, String password, String content) {
        this.username = username;
        this.password = password;
        this.content = content;
    }
}
