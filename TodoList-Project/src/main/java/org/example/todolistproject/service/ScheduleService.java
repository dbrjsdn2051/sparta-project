package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.aop.SecurePasswordService;
import org.example.todolistproject.aop.TableType;
import org.example.todolistproject.aop.ValidPassword;
import org.example.todolistproject.config.PasswordEncoder;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleDeleteAuthenticationRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleUpdateAuthenticationRequestDto;
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
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    private final WeatherService weatherService;

    @Transactional
    @SecurePasswordService
    public Long add(ScheduleCreateRequestDto scheduleDto, String tokenValue) {
        scheduleDto.setWeather(weatherService.getWeather());

        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);

        User findUser = jwtProvider.getUserByToken(tokenValue);
        schedule.addUser(findUser);

        return scheduleRepository.save(schedule).getScheduleId();
    }

    public ScheduleInfoResponseDto findOne(Long scheduleId, String tokenValue) {
        Schedule findSchedule = get(scheduleId);
        ScheduleInfoResponseDto dto = modelMapper.map(findSchedule, ScheduleInfoResponseDto.class);

        dto.setUsername(jwtProvider.getUsernameByToken(tokenValue));

        return dto;
    }

    @Transactional
    @ValidPassword(value = TableType.SCHEDULE)
    public void update(ScheduleUpdateAuthenticationRequestDto dto) {
        Schedule findSchedule = get(dto.getScheduleId());
        findSchedule.changeContent(dto.getContent());
    }

    @ValidPassword(value = TableType.SCHEDULE)
    public void delete(ScheduleDeleteAuthenticationRequestDto dto) {
        scheduleRepository.deleteById(dto.getScheduleId());
    }

    public Page<PageResponseDto> findAll(Pageable pageable) {
        return scheduleRepository.findAllWithComment(pageable);
    }

    private Schedule get(Long id) {
        return scheduleRepository.findById(id).orElseThrow(NoResultDataException::new);
    }
}
