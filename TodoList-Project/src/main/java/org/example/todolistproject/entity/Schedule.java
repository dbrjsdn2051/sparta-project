package org.example.todolistproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Size(max = 10, message = "10글자 이내로 작성해주세요.")
    private String title;
    private String password;

    @Setter
    private String content;

    @Setter
    private String weather;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addUser(User user) {
        user.getSchedules().add(this);
        this.user = user;
    }

    public Schedule(String title, String password, String content, String weather) {
        this.title = title;
        this.password = password;
        this.content = content;
        this.weather = weather;
    }
}
