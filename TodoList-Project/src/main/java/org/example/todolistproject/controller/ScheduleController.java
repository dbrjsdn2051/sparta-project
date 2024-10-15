package org.example.todolistproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.aop.CheckAuthority;
import org.example.todolistproject.config.resolver.LoginUser;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.ScheduleDto;
import org.example.todolistproject.entity.User;
import org.example.todolistproject.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<Long> join(
            @RequestBody @Valid ScheduleDto.Create dto,
            @LoginUser User user) {
        Long scheduleId = scheduleService.add(dto, user);
        return new ResponseEntity<>(scheduleId, HttpStatus.CREATED);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleDto.Response> findSchedule(@PathVariable Long scheduleId) {
        ScheduleDto.Response findSchedule = scheduleService.findOne(scheduleId);
        return ResponseEntity.ok(findSchedule);
    }

    @PatchMapping("/schedule")
    @CheckAuthority
    public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleDto.Update dto) {
        scheduleService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/schedule")
    @CheckAuthority
    public ResponseEntity<Void> deleteSchedule(@RequestBody ScheduleDto.Delete dto) {
        scheduleService.delete(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<Page<PageResponseDto>> schedules(@PageableDefault(size = 10) Pageable pageable) {
        Page<PageResponseDto> findSchedules = scheduleService.findAll(pageable);
        return ResponseEntity.ok(findSchedules);
    }
}
