package org.example.scheduleproject.repository;

import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseDetailsScheduleDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.UpdateTodoList;
import org.example.scheduleproject.entity.Schedule;
import org.example.scheduleproject.repository.rowMapper.ScheduleDetailsRowMapper;
import org.example.scheduleproject.repository.rowMapper.ScheduleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        Schedule schedule = new Schedule(scheduleId, userId, requestScheduleWithUserDto.getTodoList(), now, now);
        String sql = "insert into schedule(schedule_id, user_id, todo_list, created_at, updated_at) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                schedule.getScheduleId().toString(),
                schedule.getUserId().toString(),
                schedule.getTodoList(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    public UUID update(UUID scheduleId, UpdateTodoList updateData) {
        String sql = "update schedule set todo_list = ?, updated_at = ? where schedule_id = ?";
        Object[] params = {updateData.getTodoList(), LocalDateTime.now(), scheduleId.toString()};
        jdbcTemplate.update(sql, params);
        return scheduleId;
    }

    public List<ResponseScheduleDto> findAllSchedule(int limit, int offset) {
        String sql = "select s.schedule_id, u.user_id, s.todo_list, u.username, s.created_at, s.updated_at " +
                "from schedule s join user u on s.user_id = u.user_id order by s.updated_at desc " +
                "limit ? offset ?";
        return jdbcTemplate.query(sql, new ScheduleRowMapper(), new Object[]{limit, offset});
    }

    public Optional<ResponseDetailsScheduleDto> findById(UUID scheduleId) {
        String sql = "select s.schedule_id, s.todo_list, u.username, u.email, s.created_at, s.updated_at " +
                "from schedule s " +
                "join user u on s.user_id = u.user_id " +
                "where s.schedule_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new ScheduleDetailsRowMapper(), scheduleId.toString()));
    }

    public void delete(UUID scheduleId) {
        String sql = "delete from schedule where schedule_id = ?";
        jdbcTemplate.update(sql, scheduleId.toString());
    }

    public String findUserPasswordByScheduleId(UUID scheduleId) {
        String sql = "select u.password from user u join schedule s on u.user_id = s.user_id where s.schedule_id = ?";

        return "{bcrypt}$" + jdbcTemplate.queryForObject(sql,
                ((rs, rowNum) -> rs.getString("password")),
                scheduleId.toString());
    }


}
