package com.example.dropwizard_form_sql.mapper;


import com.example.dropwizard_form_sql.model.Sector;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectorRowMapper implements RowMapper<Sector> {
    @Override
    public Sector map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Sector(
                rs.getLong("sectors_id"),
                rs.getLong("parent_sectors_id"),
                rs.getLong("api_id"),
                rs.getLong("level"),
                rs.getString("sector_value"),
                rs.getLong("ux_order"));
    }
}