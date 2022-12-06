package com.example.dropwizard_form_sql.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorCode {

    public ErrorCode(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    @JsonProperty("fieldName")
    public String fieldName;

    @JsonProperty("errorMessage")
    public String errorMessage;
}
