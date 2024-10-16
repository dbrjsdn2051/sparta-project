package org.example.todolistproject.service;

import org.example.todolistproject.dto.schedule.ScheduleDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.factory.ScheduleFactory;
import org.example.todolistproject.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ScheduleFactory scheduleFactory;

    @InjectMocks
    ScheduleService scheduleService;

    ScheduleDto.Create createDto;
    ScheduleDto.Delete deleteDto;
    ScheduleDto.Update updateDto;

    Schedule schedule;

    User user;

    @BeforeEach
    void init() {
        createDto = new ScheduleDto.Create("title1", "content", "1234", "sunny");
        updateDto = new ScheduleDto.Update();
        updateDto.setScheduleId(1L);
        updateDto.setPassword("1234");
        updateDto.setContent("TIL");

        deleteDto = new ScheduleDto.Delete();
        deleteDto.setPassword("1234");
        deleteDto.setScheduleId(1L);

        user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .role(Role.USER)
                .email("user@naver.com")
                .build();

        schedule = Schedule.builder()
                .title("TEST1")
                .content("GOOD")
                .weather("rain")
                .password("1234")
                .user(user)
                .build();
    }

    @Test
    void addScheduleTest() {
        // given
        when(scheduleFactory.createSchedule(createDto, user)).thenReturn(schedule);
        when(scheduleRepository.save(schedule)).thenReturn(schedule);

        // when
        Long scheduleId = scheduleService.add(createDto, user);

        // then
        assertThat(scheduleId).isEqualTo(schedule.getScheduleId());

        verify(scheduleRepository, times(1)).save(schedule);
        verify(scheduleFactory, times(1)).createSchedule(any(), any());
    }

    @Test
    void updateScheduleSuccessTest() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(schedule));
        doNothing().when(scheduleFactory).updateSchedule(updateDto, schedule);

        // when
        scheduleService.update(updateDto);

        // then
        verify(scheduleRepository, times(1)).findById(1L);
        verify(scheduleFactory, times(1)).updateSchedule(updateDto, schedule);
    }

    @Test
    void updateScheduleFail_ScheduleNotFound() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoResultDataException.class, () -> scheduleService.update(updateDto));
        verify(scheduleRepository, times(1)).findById(1L);
    }

    @Test
    void updateScheduleFail_MissMatchPassword() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(schedule));
        doThrow(MissMatchPasswordException.class)
                .when(scheduleFactory)
                .updateSchedule(updateDto, schedule);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> scheduleService.update(updateDto));
        verify(scheduleRepository, times(1)).findById(1L);
        verify(scheduleFactory).updateSchedule(any(), any());
    }

    @Test
    void deleteSuccessTest() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(schedule));
        doNothing().when(scheduleFactory)
                .deleteSchedule(deleteDto, schedule);

        // when
        scheduleService.delete(deleteDto);

        // then
        verify(scheduleRepository, times(1)).findById(any());
        verify(scheduleFactory, times(1)).deleteSchedule(any(), any());
    }

    @Test
    void deleteFail_NotFoundTest() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoResultDataException.class, () -> scheduleService.delete(deleteDto));
        verify(scheduleRepository, times(1)).findById(any());
    }

    @Test
    void deleteFail_MissMatchPassword() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(schedule));
        doThrow(MissMatchPasswordException.class)
                .when(scheduleFactory)
                .deleteSchedule(deleteDto, schedule);

        // when & then
        assertThrows(MissMatchPasswordException.class, () -> scheduleService.delete(deleteDto));
    }

    @Test
    void findOnetest() {
        // given
        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(schedule));
        when(scheduleFactory.getSchedule(schedule)).thenReturn(new ScheduleDto.Response(schedule));

        // when
        ScheduleDto.Response response = scheduleService.findOne(1L);

        // then
        assertThat(response.getScheduleId()).isEqualTo(schedule.getScheduleId());
        assertThat(response.getUsername()).isEqualTo(schedule.getUser().getUsername());
        assertThat(response.getWeather()).isEqualTo(schedule.getWeather());
        assertThat(response.getTitle()).isEqualTo(schedule.getTitle());

        verify(scheduleRepository, times(1)).findById(1L);
        verify(scheduleFactory, times(1)).getSchedule(any());
    }




}