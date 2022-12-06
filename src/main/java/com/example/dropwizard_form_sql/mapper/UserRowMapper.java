package com.example.dropwizard_form_sql.mapper;


import com.example.dropwizard_form_sql.model.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new User(
                rs.getLong("user_id"),
                rs.getString("name"));
    }
}