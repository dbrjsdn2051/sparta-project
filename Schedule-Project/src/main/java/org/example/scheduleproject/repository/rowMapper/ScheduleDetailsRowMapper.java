package org.example.scheduleproject.repository.rowMapper;

import org.example.scheduleproject.dto.ResponseDetailsScheduleDto;
import org.example.scheduleproject.dto.ResponseScheduleDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ScheduleDetailsRowMapper implements RowMapper<ResponseDetailsScheduleDto> {

    @Override
    public ResponseDetailsScheduleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResponseDetailsScheduleDto responseScheduleDto = new ResponseDetailsScheduleDto();
        responseScheduleDto.setScheduleId(UUID.fromString(rs.getString("schedule_id")));
        responseScheduleDto.setUserId(UUID.fromString(rs.getString("user_id")));
        responseScheduleDto.setUsername(rs.getString("username"));
        responseScheduleDto.setEmail(rs.getString("email"));
        responseScheduleDto.setTodoList(rs.getString("todo_list"));
        responseScheduleDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        responseScheduleDto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return responseScheduleDto;
    }
}
