package org.example.scheduleproject.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

public class JdbcTemplateOptional {
    public static <T> Optional<T> queryForOptional(JdbcTemplate jdbcTemplate, String sql, RowMapper<T> rowMapper, Object[] param) {
        try {
            T t = jdbcTemplate.queryForObject(sql, rowMapper, param);
            return Optional.ofNullable(t);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
