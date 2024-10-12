package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.aop.TableType;
import org.example.todolistproject.aop.ValidPassword;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.comment.reponse.CommentInfoResponseDto;
import org.example.todolistproject.dto.comment.request.CommentCreateRequestDto;
import org.example.todolistproject.dto.comment.request.CommentDeleteRequestDto;
import org.example.todolistproject.dto.comment.request.CommentUpdateRequestDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.CommentRepository;
import org.example.todolistproject.repository.ScheduleRepository;
import org.example.todolistproject.security.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public Long add(CommentCreateRequestDto dto, Long scheduleId, String tokenValue) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        String username = jwtProvider.getUsernameByToken(tokenValue);
        dto.setUsername(username);

        Comment comment = modelMapper.map(dto, Comment.class);
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(NoResultDataException::new);
        comment.addSchedule(schedule);

        return commentRepository.save(comment).getCommentId();
    }

    public CommentInfoResponseDto findOne(Long commentId) {
        Comment findComment = get(commentId);
        return modelMapper.map(findComment, CommentInfoResponseDto.class);
    }

    @Transactional
    @ValidPassword(value = TableType.COMMENT)
    public void update(CommentUpdateRequestDto dto){
        Comment findComment = get(dto.getCommentId());
        findComment.changeContent(dto.getContent());
    }

    @ValidPassword(value = TableType.COMMENT)
    public void delete(CommentDeleteRequestDto dto){
        commentRepository.deleteById(dto.getCommentId());
    }

    private Comment get(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoResultDataException::new);
    }

}
