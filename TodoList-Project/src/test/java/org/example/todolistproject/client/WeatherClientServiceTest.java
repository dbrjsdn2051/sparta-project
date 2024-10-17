package org.example.todolistproject.client;

import org.example.todolistproject.client.dto.WeatherResponseDto;
import org.example.todolistproject.exception.NoResultDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherClientServiceTest {

    @Mock
    WeatherClient weatherClient;

    @InjectMocks
    WeatherClientService weatherClientService;

    private LocalDateTime today;

    @BeforeEach
    void setUp() {
        today = LocalDateTime.now();
    }

    @Test
    void getWeatherSuccessTest() {
        // given
        String formatDate = today.format(DateTimeFormatter.ofPattern("MM-dd"));
        WeatherResponseDto dto1 = new WeatherResponseDto("01-01", "Sunny");
        WeatherResponseDto dto2 = new WeatherResponseDto(formatDate, "Rainy");
        List<WeatherResponseDto> weatherList = Arrays.asList(dto1, dto2);

        when(weatherClient.getWeather()).thenReturn(weatherList);

        //when
        String weather = weatherClientService.getWeather(today);

        //then
        assertThat(weather).isEqualTo("Rainy");
        verify(weatherClient, times(1)).getWeather();
    }

    @Test
    void getWeatherFailTest() {
        // given
        WeatherResponseDto dto1 = new WeatherResponseDto("01-01", "Sunny");
        WeatherResponseDto dto2 = new WeatherResponseDto("02-02", "Rainy");
        List<WeatherResponseDto> weatherList = Arrays.asList(dto1, dto2);

        when(weatherClient.getWeather()).thenReturn(weatherList);

        //when & then
        assertThrows(NoResultDataException.class, () -> weatherClientService.getWeather(today));

        verify(weatherClient, times(1)).getWeather();
    }

    @Test
    void getWeatherEmptyTest() {
        //given
        when(weatherClient.getWeather()).thenReturn(Collections.emptyList());

        // when & then
        assertThrows(NoResultDataException.class, () -> weatherClientService.getWeather(today));

        verify(weatherClient, times(1)).getWeather();
    }


}