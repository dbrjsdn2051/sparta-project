package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.client.WeatherClient;
import org.example.todolistproject.client.dto.WeatherResponseDto;
import org.example.todolistproject.exception.NoResultDataException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherClientService {

    private final WeatherClient weatherClient;

    public String getWeather(LocalDateTime today) {
        String formatDate = today.format(DateTimeFormatter.ofPattern("MM-dd"));
        List<WeatherResponseDto> weatherResponseDtoList = weatherClient.getWeather();

        return weatherResponseDtoList.stream().filter(weatherResponseDto -> weatherResponseDto.getDate().equals(formatDate))
                .map(WeatherResponseDto::getWeather).findFirst().orElseThrow(NoResultDataException::new);
    }
}
