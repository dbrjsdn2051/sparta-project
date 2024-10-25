package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.ScheduleDto;
import org.example.todolistproject.entity.Comment;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
import org.example.todolistproject.factory.ScheduleFactory;
import org.example.todolistproject.repository.CommentRepository;
import org.example.todolistproject.repository.ScheduleRepository;
import org.example.todolistproject.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleFactory scheduleFactory;
    private final PasswordEncoder passwordEncoder;
    private final CommentRepository commentRepository;

    @Transactional
    public Long add(ScheduleDto.Create scheduleDto, User user) {
        Schedule schedule = scheduleFactory.createSchedule(scheduleDto, user);
        return scheduleRepository.save(schedule).getScheduleId();
    }

    public ScheduleDto.ResponseWithComment findOne(Long scheduleId) {
        Schedule findSchedule = get(scheduleId);

        List<Comment> scheduleList = commentRepository.findAllByScheduleId(scheduleId);

        return new ScheduleDto.ResponseWithComment(findSchedule, scheduleList);
    }

    @Transactional
    public void update(ScheduleDto.Update dto) {
        Schedule findSchedule = get(dto.getScheduleId());

        if (!passwordEncoder.matches(dto.getPassword(), findSchedule.getPassword())) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }

        findSchedule.changeContent(dto.getContent());
    }

    @Transactional
    public void delete(ScheduleDto.Delete dto) {
        Schedule findSchedule = get(dto.getScheduleId());

        if (!passwordEncoder.matches(dto.getPassword(), findSchedule.getPassword())) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }

        commentRepository.deleteByScheduleId(findSchedule.getScheduleId());
        scheduleRepository.deleteById(findSchedule.getScheduleId());
    }

    @Transactional
    public Page<PageResponseDto> findAll(Pageable pageable) {
        return scheduleRepository.findAllWithComment(pageable);
    }

    private Schedule get(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }
}
