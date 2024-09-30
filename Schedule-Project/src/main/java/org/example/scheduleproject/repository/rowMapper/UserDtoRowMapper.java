package org.example.scheduleproject.repository.rowMapper;

import org.example.scheduleproject.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDtoRowMapper implements RowMapper<UserDto> {

    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDto userDto = new UserDto();
        userDto.setUserId(UUID.fromString(rs.getString("user_id")));
        userDto.setPassword(rs.getString("password"));
        return userDto;
    }
}
