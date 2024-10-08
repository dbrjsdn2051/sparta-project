package org.example.todolistproject.service;

import org.assertj.core.api.Assertions;
import org.example.todolistproject.entity.Weather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherClientTest {

    @Autowired
    WeatherClient weatherClient;
    @Autowired
    WeatherService weatherService;

    @Test
    public void weatherTest(){
        List<Weather> weather = weatherClient.getWeather();
        for (Weather weather1 : weather) {
            System.out.println("weather1 = " + weather1.getDate());
        }
    }

    @Test
    public void weatherDateTest(){
        String weatherByDate = weatherService.findWeatherByDate();
        Assertions.assertThat(weatherByDate).isEqualTo("Blustery Winds");
    }

}