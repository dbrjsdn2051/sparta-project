package org.example.scheduleproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.UpdateTodoList;
import org.example.scheduleproject.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseScheduleDto> getSchedule(@PathVariable String scheduleId) {
        return ResponseEntity.ok(scheduleService.getTodoList(UUID.fromString(scheduleId)));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ResponseScheduleDto>> getAllPost() {
        return ResponseEntity.ok(scheduleService.getAllTodoList());
    }

    @PostMapping("/schedule")
    public UUID addSchedule(@RequestBody RequestScheduleWithUserDto requestScheduleWithUserDto){
        return scheduleService.save(requestScheduleWithUserDto);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public void deleteSchedule(@PathVariable String scheduleId, @RequestBody Map<String, String> request){
        scheduleService.deleteSchedule(UUID.fromString(scheduleId), request.get("password"));
    }

    @PutMapping("/schedule/{scheduleId}")
    public UUID updateSchedule(@PathVariable String scheduleId, @RequestBody UpdateTodoList updateTodoList){
        log.info("{}", updateTodoList.getTodoList());
        return scheduleService.updateSchedule(UUID.fromString(scheduleId), updateTodoList);
    }

}
