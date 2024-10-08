package org.example.todolistproject.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleDeleteRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleUpdateRequestDto;
import org.example.todolistproject.dto.schedule.response.ScheduleInfoResponseDto;
import org.example.todolistproject.entity.Role;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.AuthorizationException;
import org.example.todolistproject.exception.MissMatchPasswordException;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.exception.TokenNotFoundException;
import org.example.todolistproject.repository.ScheduleRepository;
import org.example.todolistproject.repository.UserRepository;
import org.example.todolistproject.security.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final WeatherService weatherService;

    public Long add(ScheduleCreateRequestDto scheduleDto, String tokenValue) {
        String encodedPassword = passwordEncoder.encode(scheduleDto.getPassword());
        scheduleDto.setPassword(encodedPassword);

        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        schedule.setWeather(weatherService.findWeatherByDate());

        User findUser = getUserByToken(tokenValue);
        schedule.addUser(findUser);

        return scheduleRepository.save(schedule).getScheduleId();
    }

    public ScheduleInfoResponseDto findOne(Long scheduleId) {
        Schedule findSchedule = getScheduleById(scheduleId);
        ScheduleInfoResponseDto map = modelMapper.map(findSchedule, ScheduleInfoResponseDto.class);
        map.setUsername(findSchedule.getUser().getUsername());
        return map;
    }

    public void update(ScheduleUpdateRequestDto dto, String tokenValue){
        Schedule findSchedule = getScheduleById(dto.getScheduleId());
        validPasswordAndRole(dto.getPassword(), findSchedule.getPassword(), tokenValue);
        findSchedule.setContent(dto.getContent());
    }

    public void delete(ScheduleDeleteRequestDto dto, String tokenValue) {
        Schedule findSchedule = getScheduleById(dto.getScheduleId());
        validPasswordAndRole(dto.getPassword(), findSchedule.getPassword(), tokenValue);

        scheduleRepository.deleteById(dto.getScheduleId());
    }

    public Page<PageResponseDto> findAll(Pageable pageable){
        return scheduleRepository.findAllWithComment(pageable);
    }



    private Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(NoResultDataException::new);
    }

    private void validPasswordAndRole(String password, String encodedPassword, String tokenValue) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new MissMatchPasswordException();
        }

        String token = jwtProvider.substringToken(tokenValue);
        if (!jwtProvider.validateToken(token)) {
            throw new TokenNotFoundException();
        }

        Claims info = jwtProvider.getUserInfoFromToken(token);
        String username = info.getSubject();
        User findUser = userRepository.findByUsername(username).orElseThrow(NoResultDataException::new);

        if (!findUser.getRole().equals(Role.ADMIN)) {
            throw new AuthorizationException();
        }
    }

    private User getUserByToken(String tokenValue){
        String token = jwtProvider.substringToken(tokenValue);
        Claims info = jwtProvider.getUserInfoFromToken(token);
        return userRepository.findByUsername(info.getSubject()).orElseThrow(NoResultDataException::new);
    }

}
