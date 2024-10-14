package org.example.todolistproject.service;

import org.example.todolistproject.entity.Weather;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "weatherInfo", url = "https://f-api.github.io")
public interface WeatherClient {

    @GetMapping("/f-api/weather.json")
    List<Weather> getWeather();
}
