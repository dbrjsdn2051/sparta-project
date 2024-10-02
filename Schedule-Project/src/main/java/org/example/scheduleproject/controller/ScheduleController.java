package org.example.scheduleproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseDetailsScheduleDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.UpdateTodoList;
import org.example.scheduleproject.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseDetailsScheduleDto> getSchedule(@PathVariable String scheduleId) {
        return ResponseEntity.ok().body(scheduleService.findScheduleByScheduleId(UUID.fromString(scheduleId)));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ResponseScheduleDto>> getAllSchedule(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1") int offset) {
        return ResponseEntity.ok().body(scheduleService.findAllSchedule(limit, offset));
    }

    @PostMapping("/schedule")
    public ResponseEntity<UUID> addSchedule(@RequestBody RequestScheduleWithUserDto requestScheduleWithUserDto) {
        return ResponseEntity.ok().body(scheduleService.save(requestScheduleWithUserDto));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String scheduleId, @RequestBody Map<String, String> request) throws BadRequestException {
        scheduleService.deleteSchedule(UUID.fromString(scheduleId), request.get("password"));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<UUID> updateSchedule(@PathVariable String scheduleId, @RequestBody UpdateTodoList updateTodoList) throws BadRequestException {
        return ResponseEntity.ok().body(scheduleService.updateSchedule(UUID.fromString(scheduleId), updateTodoList));
    }

}
