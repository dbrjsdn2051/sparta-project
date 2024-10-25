package org.example.todolistproject.factory;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.client.WeatherClientService;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.schedule.ScheduleDto;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleFactory {

    private final PasswordEncoder passwordEncoder;
    private final WeatherClientService weatherClientService;

    public Schedule createSchedule(ScheduleDto.Create scheduleDto, User user) {
        String encodedPassword = passwordEncoder.encode(scheduleDto.getPassword());
        String weather = weatherClientService.getWeather(LocalDateTime.now());

        return Schedule.builder()
                .title(scheduleDto.getTitle())
                .content(scheduleDto.getContent())
                .weather(weather)
                .password(encodedPassword)
                .user(user)
                .build();
    }
}