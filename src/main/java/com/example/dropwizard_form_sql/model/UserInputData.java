package com.example.dropwizard_form_sql.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserInputData {
    @JsonProperty("name")
    public String name;

    @JsonProperty("uid")
    public String uid;

    @JsonProperty("sectors")
    public List<Integer> sectors;

    @JsonProperty("terms")
    public String terms;

}
