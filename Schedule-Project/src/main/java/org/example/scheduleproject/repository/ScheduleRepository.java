package org.example.scheduleproject.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.UpdateTodoList;
import org.example.scheduleproject.repository.rowMapper.ScheduleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(UUID scheduleId, UUID userId, LocalDateTime now, RequestScheduleWithUserDto requestScheduleWithUserDto) {
        String sql = "insert into schedule(schedule_id, user_id, todo_list, created_at, updated_at) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, scheduleId.toString(), userId.toString(), requestScheduleWithUserDto.getTodoList(), now, now);
    }

    public UUID update(UUID scheduleId, UpdateTodoList updateData) {
        String sql = "update schedule set todo_list = ?, updated_at = ? where schedule_id = ?";
        jdbcTemplate.update(sql, updateData.getTodoList(), LocalDateTime.now(), String.valueOf(scheduleId));
        return scheduleId;
    }

    public List<ResponseScheduleDto> findAllSchedule() {
        String sql = "select s.schedule_id, u.user_id, s.todo_list, u.username, s.created_at, s.updated_at from schedule s join user u on s.user_id = u.user_id";
        return jdbcTemplate.query(sql, new ScheduleRowMapper());
    }

    public Optional<ResponseScheduleDto> findScheduleById(UUID scheduleId) {
        String sql = "select s.schedule_id, s.user_id, s.todo_list, u.username, s.created_at, s.updated_at from schedule s join user u on s.user_id = u.user_id where s.schedule_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), scheduleId.toString()));
    }

    public void deleteScheduleById(UUID scheduleId) {
        String sql = "delete from schedule where schedule_id = ?";
        jdbcTemplate.update(sql, String.valueOf(scheduleId));
    }


}
