package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.entity.Weather;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public String findWeatherByDate() {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        String formatDate = simpleDateFormat.format(today);

        List<Weather> weatherList = weatherClient.getWeather();

        return weatherList.stream().filter(weather -> weather.getDate().equals(formatDate))
                .map(Weather::getWeather).findFirst().orElse(null);
    }
}
