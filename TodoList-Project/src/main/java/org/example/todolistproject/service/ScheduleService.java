package org.example.todolistproject.service;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.ScheduleDto;
import org.example.todolistproject.entity.Schedule;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.exception.NoResultDataException;
import org.example.todolistproject.factory.ScheduleFactory;
import org.example.todolistproject.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleFactory scheduleFactory;

    @Transactional
    public Long add(ScheduleDto.Create scheduleDto, User user) {
        Schedule schedule = scheduleFactory.createSchedule(scheduleDto, user);
        schedule.addUser(user);
        return scheduleRepository.save(schedule).getScheduleId();
    }

    public ScheduleDto.Response findOne(Long scheduleId) {
        Schedule findSchedule = get(scheduleId);
        return scheduleFactory.getSchedule(findSchedule);

    }

    @Transactional
    public void update(ScheduleDto.Update dto) {
        Schedule findSchedule = get(dto.getScheduleId());
        scheduleFactory.updateSchedule(dto, findSchedule);
    }

    public void delete(ScheduleDto.Delete dto) {
        Schedule schedule = get(dto.getScheduleId());
        scheduleFactory.deleteSchedule(dto, schedule);
        scheduleRepository.deleteById(dto.getScheduleId());
    }

    public Page<PageResponseDto> findAll(Pageable pageable) {
        return scheduleRepository.findAllWithComment(pageable);
    }

    private Schedule get(Long id) {
        return scheduleRepository.findById(id).orElseThrow(NoResultDataException::new);
    }
}
