package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.comment.CommentDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
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
    private final PasswordEncoder passwordEncoder;

    public Long add(CommentDto.Create dto, Long scheduleId, User user) {
        dto.setUsername(user.getUsername());

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = commentFactory.createComment(dto, schedule);

        return commentRepository.save(comment).getCommentId();
    }

    public CommentDto.Response findOne(Long commentId) {
        Comment findComment = get(commentId);
        return commentFactory.getComment(findComment);
    }

    @Transactional
    public void update(CommentDto.Update dto) {
        Comment findComment = get(dto.getCommentId());

        if (!passwordEncoder.matches(dto.getPassword(), findComment.getPassword())) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }

        findComment.changeContent(dto.getContent());
    }

    public void delete(CommentDto.Delete dto) {
        Comment findComment = get(dto.getCommentId());

        if (!passwordEncoder.matches(dto.getPassword(), findComment.getPassword())) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }

        commentRepository.deleteById(dto.getCommentId());
    }

    private Comment get(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

}
