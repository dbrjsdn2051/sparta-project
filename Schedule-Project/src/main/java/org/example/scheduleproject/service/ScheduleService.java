package org.example.scheduleproject.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.scheduleproject.controller.exception.NoRequestException;
import org.example.scheduleproject.dto.*;
import org.example.scheduleproject.repository.ScheduleRepository;
import org.example.scheduleproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public UUID save(RequestScheduleWithUserDto requestScheduleWithUserDto) {
        UUID scheduleId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        requestScheduleWithUserDto.setPassword(passwordEncoder.encode(requestScheduleWithUserDto.getPassword()));

        Optional<UserDto> findUser = userRepository.findUserByUsername(requestScheduleWithUserDto.getUsername());

        if (findUser.isPresent()) {
            scheduleRepository.add(scheduleId, findUser.get().getUserId(), now, requestScheduleWithUserDto);
            return scheduleId;
        }

        UUID userId = UUID.randomUUID();
        userRepository.add(scheduleId, userId, now, requestScheduleWithUserDto);
        scheduleRepository.add(scheduleId, userId, now, requestScheduleWithUserDto);
        return scheduleId;
    }


    public ResponseDetailsScheduleDto findOneSchedule(UUID scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NoRequestException("데이터가 존재하지 않아요!!"));
    }

    public List<ResponseScheduleDto> findAllSchedule(int limit, int offset) {
        return scheduleRepository.findAllSchedule(limit, (offset - 1) * limit);
    }

    public void deleteSchedule(UUID scheduleId, String requestPassword) throws BadRequestException {
        validPassword(scheduleId, requestPassword);
        scheduleRepository.delete(scheduleId);
    }

    public UUID updateSchedule(UUID scheduleId, UpdateTodoList updateTodoList) throws BadRequestException {
        validPassword(scheduleId, updateTodoList.getPassword());
        return scheduleRepository.update(scheduleId, updateTodoList);
    }


    private String findUserPasswordById(UUID scheduleId) {
        return scheduleRepository.findUserPasswordByScheduleId(scheduleId);
    }

    private void validPassword(UUID scheduleId, String requestPassword) throws BadRequestException {
        String password = findUserPasswordById(scheduleId);
        if (passwordEncoder.matches(requestPassword, password)) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
    }


}
