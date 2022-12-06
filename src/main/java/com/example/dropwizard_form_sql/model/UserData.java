package com.example.dropwizard_form_sql.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserData {
    @JsonProperty("user_data_id")
    long userDataId;
    @JsonProperty("uid")
    String uid;
    @JsonProperty("sectors")
    List<Integer> sectors;
    @JsonProperty("terms")
    String terms;
    @JsonProperty("user_id")
    long userId;

    public UserData(long userDataId, String uid, List<Integer> sectors, String terms, long userId) {
        this.userDataId = userDataId;
        this.uid = uid;
        this.sectors = sectors;
        this.terms = terms;
        this.userId = userId;
    }

}
