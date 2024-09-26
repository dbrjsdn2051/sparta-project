package org.example.scheduleproject.repository.rowMapper;

import org.example.scheduleproject.dto.ResponseUserDto;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserResponseRowMapper implements RowMapper<ResponseUserDto> {
    @Override
    public ResponseUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setScheduleId(UUID.fromString(rs.getString("schedule_id")));
        responseUserDto.setUserId(UUID.fromString("user_id"));
        responseUserDto.setUsername(rs.getString("username"));
        responseUserDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        responseUserDto.setCreatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return responseUserDto;
    }
}
