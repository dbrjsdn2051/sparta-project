package org.example.todolistproject.factory;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFactory {

    private final PasswordEncoder passwordEncoder;

    public Comment createComment(CommentDto.Create commentDto, Schedule schedule) {
        String encodedPassword = passwordEncoder.encode(commentDto.getPassword());

        return Comment.builder()
                .username(commentDto.getUsername())
                .password(encodedPassword)
                .content(commentDto.getContent())
                .schedule(schedule)
                .build();
    }

    public void updateComment(CommentDto.Update commentDto, Comment comment) {
        passwordEncoder.validPassword(commentDto.getPassword(), comment.getPassword());
        comment.changeContent(commentDto.getContent());
    }

    public void deleteComment(CommentDto.Delete commentDto, Comment comment) {
        passwordEncoder.validPassword(commentDto.getPassword(), comment.getPassword());
    }

    public CommentDto.Response getComment(Comment comment) {
        return new CommentDto.Response(comment);
    }
}
