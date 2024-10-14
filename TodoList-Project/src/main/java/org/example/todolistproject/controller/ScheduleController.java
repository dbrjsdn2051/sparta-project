package org.example.todolistproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolistproject.aop.CheckAuthority;
import org.example.todolistproject.dto.page.PageResponseDto;
import org.example.todolistproject.dto.schedule.request.ScheduleCreateRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleDeleteAuthenticationRequestDto;
import org.example.todolistproject.dto.schedule.request.ScheduleUpdateAuthenticationRequestDto;
import org.example.todolistproject.dto.schedule.response.ScheduleInfoResponseDto;
import org.example.todolistproject.security.JwtProvider;
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
            @RequestBody @Valid ScheduleCreateRequestDto dto,
            @CookieValue(JwtProvider.AUTHORIZATION_HEADER) String tokenValue)
    {
        Long scheduleId = scheduleService.add(dto, tokenValue);
        return new ResponseEntity<>(scheduleId, HttpStatus.CREATED);
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
    @CheckAuthority
    public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleUpdateAuthenticationRequestDto dto) {
        scheduleService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/schedule")
    @CheckAuthority
    public ResponseEntity<Void> deleteSchedule(@RequestBody ScheduleDeleteAuthenticationRequestDto dto) {
        scheduleService.delete(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<Page<PageResponseDto>> schedules(@PageableDefault(size = 10) Pageable pageable) {
        Page<PageResponseDto> findSchedules = scheduleService.findAll(pageable);
        return ResponseEntity.ok(findSchedules);
    }
}
