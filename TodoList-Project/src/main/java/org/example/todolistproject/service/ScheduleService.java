package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleDeleteRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleUpdateRequestDto;
import org.example.todolistproject.dto.schedule.response.ScheduleInfoResponseDto;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.repository.ScheduleRepository;
import org.example.todolistproject.security.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final WeatherService weatherService;

    @Transactional
    public Long add(ScheduleCreateRequestDto scheduleDto, String tokenValue) {
        String encodedPassword = passwordEncoder.encode(scheduleDto.getPassword());

        scheduleDto.setPassword(encodedPassword);
        scheduleDto.setWeather(weatherService.getWeather());

        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);

        User findUser = jwtProvider.getUserByToken(tokenValue);
        schedule.addUser(findUser);

        return scheduleRepository.save(schedule).getScheduleId();
    }

    public ScheduleInfoResponseDto findOne(Long scheduleId, String tokenValue) {
        Schedule findSchedule = getScheduleById(scheduleId);
        ScheduleInfoResponseDto dto = modelMapper.map(findSchedule, ScheduleInfoResponseDto.class);

        dto.setUsername(jwtProvider.getUsernameByToken(tokenValue));

        return dto;
    }

    @Transactional
    public void update(ScheduleUpdateRequestDto dto){
        Schedule findSchedule = getScheduleById(dto.getScheduleId());
        passwordEncoder.validPassword(dto.getPassword(), findSchedule.getPassword());
        findSchedule.changeContent(dto.getContent());
    }

    public void delete(ScheduleDeleteRequestDto dto) {
        Schedule findSchedule = getScheduleById(dto.getScheduleId());
        passwordEncoder.validPassword(dto.getPassword(), findSchedule.getPassword());
        scheduleRepository.deleteById(dto.getScheduleId());
    }

    public Page<PageResponseDto> findAll(Pageable pageable){
        return scheduleRepository.findAllWithComment(pageable);
    }

    private Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(NoResultDataException::new);
    }
}
