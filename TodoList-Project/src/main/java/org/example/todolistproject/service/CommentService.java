package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.factory.CommentFactory;
import org.example.todolistproject.repository.CommentRepository;
import org.example.todolistproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentFactory commentFactory;


    @Transactional
    public Long add(CommentDto.Create dto, Long scheduleId, User user) {
        dto.setUsername(user.getUsername());

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(NoResultDataException::new);

        Comment comment = commentFactory.createComment(dto, schedule);
        comment.addSchedule(schedule);

        return commentRepository.save(comment).getCommentId();
    }

    public CommentDto.Response findOne(Long commentId) {
        Comment findComment = get(commentId);
        return commentFactory.getComment(findComment);
    }

    @Transactional
    public void update(CommentDto.Update dto) {
        Comment findComment = get(dto.getCommentId());
        commentFactory.updateComment(dto, findComment);
    }

    public void delete(CommentDto.Delete dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(NoResultDataException::new);
        commentFactory.deleteComment(dto, comment);
        commentRepository.deleteById(dto.getCommentId());
    }

    private Comment get(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoResultDataException::new);
    }

}
