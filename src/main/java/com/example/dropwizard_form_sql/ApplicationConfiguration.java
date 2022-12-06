package com.example.dropwizard_form_sql;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class ApplicationConfiguration extends Configuration {
    @NotNull
    private final int defaultSize;

    @JsonCreator
    public ApplicationConfiguration(@JsonProperty("defaultSize") int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getDefaultSize() {
        return defaultSize;
    }


}