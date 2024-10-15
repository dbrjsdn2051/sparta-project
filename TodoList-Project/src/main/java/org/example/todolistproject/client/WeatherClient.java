package org.example.todolistproject.client;

import org.example.todolistproject.client.dto.Weather;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "weatherInfo", url = "https://f-api.github.io")
public interface WeatherClient {

    @GetMapping("/f-api/weather.json")
    List<Weather> getWeather();
}
