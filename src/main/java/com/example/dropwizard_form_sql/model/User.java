package com.example.dropwizard_form_sql.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("user_id")
    long userId;
    @JsonProperty("name")
    String name;

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
