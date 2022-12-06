package com.example.dropwizard_form_sql.mapper;


import com.example.dropwizard_form_sql.model.UserData;
import com.example.dropwizard_form_sql.utils.Utils;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDataRowMapper implements RowMapper<UserData> {

    @Override
    public UserData map(ResultSet rs, StatementContext ctx) throws SQLException {
        var sectors = rs.getString("sectors");
        List<Integer> sectorList = Utils.sectorList(sectors);

        return new UserData(
                rs.getLong("user_data_id"),
                rs.getString("uid"),
                sectorList,
                rs.getString("terms"),
                rs.getLong("user_id"));
    }
}