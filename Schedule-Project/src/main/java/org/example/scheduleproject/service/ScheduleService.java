package org.example.scheduleproject.service;

import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.UpdateTodoList;
import org.example.scheduleproject.repository.ScheduleRepository;
import org.example.scheduleproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID save(RequestScheduleWithUserDto requestScheduleWithUserDto) {
        UUID scheduleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        userRepository.add(scheduleId, userId, now, requestScheduleWithUserDto);
        scheduleRepository.add(scheduleId, userId, now, requestScheduleWithUserDto);
        return scheduleId;
    }


    public Optional<ResponseScheduleDto> getTodoList(UUID scheduleId) {
        return scheduleRepository.findScheduleById(scheduleId);
    }

    public List<ResponseScheduleDto> getAllTodoList() {
        return scheduleRepository.findAllSchedule();
    }

    public void deleteSchedule(UUID scheduleId) {
        scheduleRepository.deleteScheduleById(scheduleId);
    }

    public UUID updateSchedule(UUID scheduleId, UpdateTodoList updateTodoList) {
        return scheduleRepository.update(scheduleId, updateTodoList);
    }

}
