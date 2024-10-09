package org.example.todolistproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleDeleteRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleUpdateRequestDto;
import org.example.todolistproject.dto.schedule.response.ScheduleInfoResponseDto;
import org.example.todolistproject.security.JwtProvider;
import org.example.todolistproject.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<Long> join(
            @RequestBody ScheduleCreateRequestDto dto,
            @CookieValue(JwtProvider.AUTHORIZATION_HEADER) String tokenValue)
    {
        Long scheduleId = scheduleService.add(dto, tokenValue);
        return ResponseEntity.ok(scheduleId);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleInfoResponseDto> findSchedule(
            @PathVariable Long scheduleId,
            @CookieValue(JwtProvider.AUTHORIZATION_HEADER) String tokenValue)
    {
        ScheduleInfoResponseDto dto = scheduleService.findOne(scheduleId, tokenValue );
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/schedule")
    public ResponseEntity<Void> updateSchedule(
            @RequestBody ScheduleUpdateRequestDto dto,
            @CookieValue(JwtProvider.AUTHORIZATION_HEADER) String tokenValue)
    {
        scheduleService.update(dto, tokenValue);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/schedule")
    public ResponseEntity<Void> deleteSchedule(
            @RequestBody ScheduleDeleteRequestDto dto,
            @CookieValue(JwtProvider.AUTHORIZATION_HEADER) String tokenValue)
    {
        scheduleService.delete(dto, tokenValue);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<Page<PageResponseDto>> schedules(@PageableDefault(size = 10) Pageable pageable) {
        Page<PageResponseDto> findSchedules = scheduleService.findAll(pageable);
        return ResponseEntity.ok(findSchedules);
    }
}
