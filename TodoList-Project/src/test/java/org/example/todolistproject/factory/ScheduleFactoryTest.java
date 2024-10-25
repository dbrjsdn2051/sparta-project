package org.example.todolistproject.factory;

import org.example.todolistproject.client.WeatherClientService;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.schedule.ScheduleDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleFactoryTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    WeatherClientService weatherClientService;

    @InjectMocks
    ScheduleFactory scheduleFactory;

    ScheduleDto.Create createDto;
    ScheduleDto.Update updateDto;
    ScheduleDto.Delete deleteDto;
    Schedule schedule ;


    @BeforeEach
    void init() {
        createDto = new ScheduleDto.Create("title1", "content", "1234", "sunny");

        updateDto = new ScheduleDto.Update();
        updateDto.setScheduleId(1L);
        updateDto.setPassword("1234");
        updateDto.setContent("content1234");

        deleteDto = new ScheduleDto.Delete();
        deleteDto.setScheduleId(1L);
        deleteDto.setPassword("1234");

        User user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .role(Role.USER)
                .email("user@naver.com")
                .build();


        schedule = Schedule.builder()
                .title("Main Title1")
                .content("TIL")
                .weather("rain")
                .password("encodedPassword")
                .user(user)
                .build();
    }

    @Test
    void createSuccessTest(){

        //given
        String password = createDto.getPassword();
        String encodedPassword = "securedPassword";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(weatherClientService.getWeather(any())).thenReturn("Sunny");

        // when
        Schedule createSchedule = scheduleFactory.createSchedule(createDto, schedule.getUser());

        // then
        assertThat(createSchedule.getContent()).isEqualTo(createDto.getContent());
        assertThat(createSchedule.getTitle()).isEqualTo(createDto.getTitle());
        assertThat(createSchedule.getWeather()).isEqualTo("Sunny");
        assertThat(createSchedule.getPassword()).isEqualTo(encodedPassword);

        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    void updateSuccessTest(){
        // given

        // when
        scheduleFactory.updateSchedule(updateDto, schedule);

        // then
        assertThat(schedule.getContent()).isEqualTo(updateDto.getContent());
        verify(passwordEncoder, times(1)).validPassword(any(), any());
    }


    @Test
    void updateFailTest(){
        // given
        doThrow(MissMatchPasswordException.class)
                .when(passwordEncoder)
                .validPassword(updateDto.getPassword(), schedule.getPassword());
        // when & then
        assertThrows(MissMatchPasswordException.class, () -> scheduleFactory.updateSchedule(updateDto, schedule));
    }

    @Test
    void deleteSuccessTest(){
        // given

        // when
        scheduleFactory.deleteSchedule(deleteDto, schedule);

        // then
        verify(passwordEncoder, times(1)).validPassword(any(), any());
    }

    @Test
    void deleteFailTest(){
        // given
        doThrow(MissMatchPasswordException.class)
                .when(passwordEncoder)
                .validPassword(deleteDto.getPassword(), schedule.getPassword());

        // when & then
        assertThrows(MissMatchPasswordException.class,
                () -> scheduleFactory.deleteSchedule(deleteDto, schedule));
    }


}