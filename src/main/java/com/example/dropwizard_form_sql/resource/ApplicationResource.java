package com.example.dropwizard_form_sql.resource;

import com.example.dropwizard_form_sql.dao.SectorsDao;

import com.codahale.metrics.annotation.Timed;
import com.example.dropwizard_form_sql.model.Sector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/sectors")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
    private final SectorsDao sectorsDao;

    public ApplicationResource(SectorsDao sectorsDao) {
        this.sectorsDao = sectorsDao;
    }

    // get sectors from database
    @GET
    @Timed
    public List<Sector> getSectors() {
        return sectorsDao.getAllSectors();
    }
}