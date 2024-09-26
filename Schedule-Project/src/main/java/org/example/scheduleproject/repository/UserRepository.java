package org.example.scheduleproject.repository;

import org.example.scheduleproject.dto.*;
import org.example.scheduleproject.entity.User;
import org.example.scheduleproject.repository.rowMapper.UserResponseRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public UserDto findUserPasswordById(UUID userId) {
        String sql = "select user_id, password from user where user_id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<UserDto>() {
            @Override
            public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserDto userDto = new UserDto();
                userDto.setUserId(UUID.fromString(rs.getString("user_id")));
                userDto.setPassword(rs.getString("password"));
                return userDto;
            }
        }, userId.toString());
    }

    public void deleteUser(UUID scheduleId){
        String sql = "delete from user where schedule_id = ?";
        jdbcTemplate.update(sql, scheduleId.toString());
    }
}