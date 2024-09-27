package org.example.scheduleproject.service;

import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.example.scheduleproject.dto.*;
import org.example.scheduleproject.repository.ScheduleRepository;
import org.example.scheduleproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleServiceTest {


    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;

    private UUID scheduleId;
    private UUID userId;

    @BeforeEach
    public void init() {
        RequestScheduleWithUserDto requestScheduleWithUserDto1 = new RequestScheduleWithUserDto();
        requestScheduleWithUserDto1.setUsername("user1");
        requestScheduleWithUserDto1.setPassword("1234");
        requestScheduleWithUserDto1.setTodoList("hi");
        requestScheduleWithUserDto1.setEmail("user1@naver.com");
        scheduleId = UUID.randomUUID();
        userId = UUID.randomUUID();
        userRepository.add(scheduleId, userId, LocalDateTime.now(), requestScheduleWithUserDto1);
        scheduleRepository.add(scheduleId, userId, LocalDateTime.now(), requestScheduleWithUserDto1);
    }

    @Test
    public void saveTest() {
        ResponseDetailsScheduleDto findSchedule = scheduleService.findOneSchedule(scheduleId);
        assertThat(findSchedule.getScheduleId()).isEqualTo(scheduleId);
    }

    @Test
    public void findOne() {
        ResponseDetailsScheduleDto todoList = scheduleService.findOneSchedule(scheduleId);
        UserDto findUser = userRepository.findUserByuUerId(userId);
    }

    @Test
    public void update() {
        UpdateTodoList updateTodoList = new UpdateTodoList();
        updateTodoList.setTodoList("TIL 작성");
        updateTodoList.setPassword("1234");
        try {
            scheduleService.updateSchedule(scheduleId, updateTodoList);
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getMessage());
        }

        ResponseDetailsScheduleDto findSchedule = scheduleService.findOneSchedule(scheduleId);
        assertThat(findSchedule.getTodoList()).isEqualTo(updateTodoList.getTodoList());
    }

    @Test
    public void delete() {
        try {
            scheduleService.deleteSchedule(scheduleId, "1234");
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertThatThrownBy(() -> scheduleService.findOneSchedule(scheduleId))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findAll(){
        List<ResponseScheduleDto> list = scheduleService.findAllSchedule(10, 1);
        assertThat(list.size()).isEqualTo(10);
    }
}