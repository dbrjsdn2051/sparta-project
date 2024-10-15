package org.example.todolistproject.client;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.client.dto.Weather;
import org.example.todolistproject.exception.NoResultDataException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherClientService {

    private final WeatherClient weatherClient;

    public String getWeather(LocalDateTime today) {
        String formatDate = today.format(DateTimeFormatter.ofPattern("MM-dd"));

        List<Weather> weatherList = weatherClient.getWeather();

        return weatherList.stream().filter(weather -> weather.getDate().equals(formatDate))
                .map(Weather::getWeather).findFirst().orElseThrow(NoResultDataException::new);
    }
}
