package com.example.dropwizard_form_sql.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sector {
    long sectorsId;
    long parentSectorsId;
    @JsonProperty("api_id")
    long apiId;
    @JsonProperty("level")
    long level;
    @JsonProperty("sector_value")
    String sectorValue;
    long uxOrder;

    public Sector(long sectorsId, long parentSectorsId, long apiId, long level, String sectorValue, long uxOrder) {
        this.apiId = apiId;
        this.level = level;
        this.sectorValue = sectorValue;
    }
}
