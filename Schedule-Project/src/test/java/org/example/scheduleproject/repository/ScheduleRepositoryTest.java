package org.example.scheduleproject.repository;

import org.example.scheduleproject.controller.exception.NoRequestException;
import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseDetailsScheduleDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.UpdateTodoList;
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
class ScheduleRepositoryTest {

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

        RequestScheduleWithUserDto requestScheduleWithUserDto2 = new RequestScheduleWithUserDto();
        requestScheduleWithUserDto2.setUsername("user2");
        requestScheduleWithUserDto2.setPassword("1234");
        requestScheduleWithUserDto2.setTodoList("hello");
        requestScheduleWithUserDto2.setEmail("user2@naver.com");
        UUID scheduleId2 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        userRepository.add(scheduleId2, userId2, LocalDateTime.now(), requestScheduleWithUserDto2);
        scheduleRepository.add(scheduleId2, userId2, LocalDateTime.now(), requestScheduleWithUserDto2);
    }


    @Test
    public void selectOneIdTest() {
        ResponseDetailsScheduleDto scheduleById = scheduleRepository.findById(scheduleId).orElseThrow();
        assertThat(scheduleId).isEqualTo(scheduleById.getScheduleId());


        String scheduleByUserId = scheduleRepository.findUserPasswordByScheduleId(scheduleId);
        assertThat(scheduleByUserId).isEqualTo("{bcrypt}$1234");
    }

    @Test
    public void updateTest(){
        UpdateTodoList updateTodoList = new UpdateTodoList();
        updateTodoList.setPassword("1234");
        updateTodoList.setTodoList("Hello World");

        scheduleRepository.update(scheduleId, updateTodoList);

        ResponseDetailsScheduleDto findSchedule = scheduleRepository.findById(scheduleId).orElseThrow();

        assertThat(findSchedule.getTodoList()).isEqualTo("Hello World");
    }

    @Test
    public void deleteTest(){
        scheduleRepository.delete(scheduleId);
        assertThatThrownBy(() -> scheduleRepository.findById(scheduleId))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findAllTest(){
        List<ResponseScheduleDto> schedules = scheduleRepository.findAllSchedule(10, 1);
        assertThat(schedules.size()).isEqualTo(10);
    }


}