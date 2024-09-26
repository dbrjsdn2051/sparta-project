package org.example.scheduleproject.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.scheduleproject.dto.*;
import org.example.scheduleproject.repository.ScheduleRepository;
import org.example.scheduleproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    @Transactional
    public UUID save(RequestScheduleWithUserDto requestScheduleWithUserDto) {
        UUID scheduleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        userRepository.add(scheduleId, userId, now, requestScheduleWithUserDto);
        scheduleRepository.add(scheduleId, userId, now, requestScheduleWithUserDto);
        return scheduleId;
    }


    public ResponseDetailsScheduleDto getTodoList(UUID scheduleId) {
        return scheduleRepository.findScheduleById(scheduleId);
    }

    public List<ResponseScheduleDto> getAllTodoList(int limit, int offset) {
        return scheduleRepository.findAllSchedule(limit, (offset - 1) * limit);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId, String requestPassword) throws BadRequestException {
        String password = findUserById(scheduleId);
        if (!password.equals(requestPassword)) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.deleteScheduleById(scheduleId);
        userRepository.deleteUser(scheduleId);
    }

    public UUID updateSchedule(UUID scheduleId, UpdateTodoList updateTodoList) throws BadRequestException {
        String password = findUserById(scheduleId);

        if (!password.equals(updateTodoList.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        return scheduleRepository.update(scheduleId, updateTodoList);
    }

    private String findUserById(UUID scheduleId) {
        return scheduleRepository.findSchedulePasswordByUserId(scheduleId);
    }

}
