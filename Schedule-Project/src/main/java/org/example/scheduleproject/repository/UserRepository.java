package org.example.scheduleproject.repository;

import org.example.scheduleproject.dto.RequestScheduleWithUserDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.example.scheduleproject.dto.ResponseUserDto;
import org.example.scheduleproject.repository.rowMapper.UserResponseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void add(UUID scheduleId, UUID userId, LocalDateTime now, RequestScheduleWithUserDto dto) {
        String sql = "insert into user(user_id, schedule_id, username, password, email, created_at, updated_at) values(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId.toString(), scheduleId.toString(), dto.getUsername(), dto.getPassword(), dto.getEmail(), now, now);
    }


    public List<ResponseUserDto> findAllUserBySchedules(UUID userId) {
        String sql = "select u.username, s.todo_list, s.created_at, s.updated_at, u.user_id, s.schedule_id from schedule s join user u on s.user_id = u.user_id where user_id = ?";
        return jdbcTemplate.query(sql, new UserResponseRowMapper(), String.valueOf(userId));
    }


}
