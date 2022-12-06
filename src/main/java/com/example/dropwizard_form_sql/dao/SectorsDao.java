package com.example.dropwizard_form_sql.dao;



import com.example.dropwizard_form_sql.mapper.SectorRowMapper;
import com.example.dropwizard_form_sql.model.Sector;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;

public interface SectorsDao {
    String getAllSectors = "select * from sectors order by ux_order asc";

    String getSectors = "select * from sectors where api_id = :api_id";

    @SqlQuery(getAllSectors)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(SectorRowMapper.class)
    List<Sector> getAllSectors();

    @SqlQuery(getSectors)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(SectorRowMapper.class)
    List<Sector> getSectors(@Bind("api_id") long api_id);
}
