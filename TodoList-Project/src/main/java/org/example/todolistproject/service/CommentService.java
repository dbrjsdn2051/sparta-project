package org.example.todolistproject.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.comment.reponse.CommentInfoResponseDto;
import org.example.todolistproject.dto.comment.request.CommentCreateRequestDto;
import org.example.todolistproject.dto.comment.request.CommentDeleteRequestDto;
import org.example.todolistproject.dto.comment.request.CommentUpdateRequestDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.exception.MissMatchPasswordException;
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


    public Long add(CommentCreateRequestDto dto, Long scheduleId, String tokenValue) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        String username = getUsernameByTokenValue(tokenValue);
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
    public void update(CommentUpdateRequestDto dto){
        Comment findComment = get(dto.getCommentId());
        validPassword(dto.getPassword(), findComment.getPassword());
        findComment.changeContent(dto.getContent());
    }

    public void delete(CommentDeleteRequestDto dto){
        Comment findComment = get(dto.getCommentId());
        validPassword(dto.getPassword(), findComment.getPassword());
        commentRepository.deleteById(dto.getCommentId());
    }

    private void validPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new MissMatchPasswordException();
        }
    }

    private Comment get(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoResultDataException::new);
    }

    private String getUsernameByTokenValue(String tokenValue){
        String token = jwtProvider.substringToken(tokenValue);
        Claims info = jwtProvider.getUserInfoFromToken(token);
        return info.getSubject();
    }
}
