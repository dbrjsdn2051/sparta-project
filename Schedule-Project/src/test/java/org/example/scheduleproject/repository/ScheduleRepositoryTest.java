package org.example.scheduleproject.repository;

import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseDetailsScheduleDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void selectIdTest(){
        RequestScheduleWithUserDto requestScheduleWithUserDto = new RequestScheduleWithUserDto();
        requestScheduleWithUserDto.setUsername("user1");
        requestScheduleWithUserDto.setPassword("1234");
        requestScheduleWithUserDto.setTodoList("hi");
        requestScheduleWithUserDto.setEmail("d@D");
        UUID scheduleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        userRepository.add(scheduleId, userId, LocalDateTime.now(), requestScheduleWithUserDto);
        scheduleRepository.add(scheduleId, userId, LocalDateTime.now(), requestScheduleWithUserDto);

        ResponseDetailsScheduleDto scheduleById = scheduleRepository.findScheduleById(scheduleId);
        System.out.println("findScheduleId = " + scheduleById.getScheduleId());


        String scheduleByUserId = scheduleRepository.findSchedulePasswordByUserId(scheduleId);
        System.out.println("scheduleByUserId = " + scheduleByUserId);
    }

}