package com.example.dropwizard_form_sql.resource;


import com.codahale.metrics.annotation.Timed;
import com.example.dropwizard_form_sql.dao.SectorsDao;
import com.example.dropwizard_form_sql.dao.UserDao;
import com.example.dropwizard_form_sql.model.UserInputData;
import com.example.dropwizard_form_sql.utils.ApiDefinitions;
import com.example.dropwizard_form_sql.utils.ErrorCode;
import com.example.dropwizard_form_sql.utils.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.jdbi.v3.core.JdbiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Path(ApiDefinitions.UserData)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubmitDataResource {
    static final Logger logger = LoggerFactory.getLogger(SubmitDataResource.class);
    private final SectorsDao sectorsDao;
    private final UserDao userDao;

    private final ObjectMapper mapper = new ObjectMapper();

    public SubmitDataResource(SectorsDao sectorsDao, UserDao userDao) {
        this.sectorsDao = sectorsDao;
        this.userDao = userDao;
    }

    // to avoid exposing implementation specific errors while getting bad request
    static class EmptyEntity {
        @JsonProperty("ignored")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String ignored;
    }

    // no get method because webpage prevents itself from refreshing so all the data in form
    // that was submitted is still displayed on the webpage

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response onSubmitData(String reqData) {
        return updateOrInsertUserData(reqData, new Function<UserInputData, Response>() {
            @Override
            public Response apply(UserInputData userReq) {
                if (userReq != null) {
                    // api/userData/{uid} post the data here
                    var newUserDataUri = URI.create(ApiDefinitions.UserData.concat("/").concat(userReq.uid));
                    return Response.created(newUserDataUri)
                            .build();
                } else {
                    // avoid exposing implementation specific errors
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new EmptyEntity())
                            .build();
                }
            }
        });
    }

    @PUT
    @Path("{uid}")
    @Timed
    public Response onUpdateData(@PathParam("uid") String uid, String reqData) {
        return updateOrInsertUserData(reqData, new Function<UserInputData, Response>() {
            @Override
            public Response apply(UserInputData userInputData) {
                return Response.ok()
                        .build();
            }
        });
    }

    //delete via api requests only
    @DELETE
    @Path("{userName}")
    @Timed
    public Response onDeleteData(@PathParam("userName") String userName) {
        deleteDataByUserName(userName);
        return Response.ok()
                .build();
    }

    //
    //
    //

    enum DbError {
        None,
        UserExists,
        GenericError
    }


    private Response updateOrInsertUserData(String reqData, Function<UserInputData, Response> responseMapper) {

        // errorcodes are needed to display the errors on the webpage
        List<ErrorCode> errorCodeList = new ArrayList<>();
        try {
            var userReq = mapper.readValue(reqData, UserInputData.class);
            logger.debug("userReq", userReq.name);

            // validate data
            var validationErrors= validateData(userReq);
            if (validationErrors.isEmpty()) {
                // if there were no validation errors insert or update data if there is one for current session
                var dbError = updateOrInsertUserData(userReq);
                if (dbError == DbError.None) {
                    return responseMapper.apply(userReq);
                } else {
                    // if there was already user with this name on other session before
                    errorCodeList.add(new ErrorCode("name", "User already exists"));
                }
            }
            // to return all error codes
            errorCodeList.addAll(validationErrors);
        } catch (JsonProcessingException e) {
            // api request errors
            logger.error("updateOrInsertUserData {}", e.getMessage());

            if (e instanceof JsonMappingException jme) {
                logger.error(jme.getMessage());
                for (int i = 0; i < jme.getPath().size(); i++) {
                    // api errors
                    errorCodeList.add(new ErrorCode("apiError", jme.getPath().get(i).getFieldName()));
                }
            }
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorCodeList)
                .build();
    }

    // data validation
    private List<ErrorCode> validateData(UserInputData userReq) {
        List<ErrorCode> errorCodeList = new ArrayList<>();

        // check if terms are null or empty (api request)
        if (Strings.isNullOrEmpty(userReq.terms)) {
            errorCodeList.add(new ErrorCode("terms", "Terms are null or empty"));
        }
        boolean terms = Boolean.parseBoolean(userReq.terms);
        // if terms were unchecked in the form
        if (!terms) {
            errorCodeList.add(new ErrorCode("terms", "Terms are unchecked"));
        }
        // if the name is empty in the form or api request
        if (Strings.isNullOrEmpty(userReq.name)) {
            errorCodeList.add(new ErrorCode("name", "Name is empty"));
        }
        // if there are sectors that are not in the database (api request)
        if (!checkReqSectors(userReq)) {
            errorCodeList.add(new ErrorCode("sectors", "Sectors are incorrect"));
        }
        // if there are no selected sectors in the form or api request
        if (userReq.sectors.isEmpty()) {
            errorCodeList.add(new ErrorCode("sectors", "Sectors are empty"));
        }

        // return error codes and display them on the webpage
        return errorCodeList.isEmpty() ? Collections.emptyList() : errorCodeList;
    }

    private DbError insertData(UserInputData userReq) {
        // concat req.sectors into comma separated string
        String concatSectors = Utils.concatSectors(userReq.sectors);
        // generate uid
        userReq.uid = Utils.generateUid();
        Boolean terms = Boolean.parseBoolean(userReq.terms);

        // try to insert data and if there is error from request get the message
        try {
            this.userDao.insertData(userReq.uid, userReq.name, concatSectors, terms);
            return DbError.None;
        } catch (JdbiException e) {
            logger.error("insertData {}", e.getMessage());
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException constrEx) {
                // dont want to give out this error
                if (constrEx.getMessage().contains("Duplicate entry") && constrEx.getMessage().contains("key 'users.name_UNIQUE'")) {
                    return DbError.UserExists;
                }
            }
        }
        return DbError.GenericError;
    }

    // DbError for sake of errors that were because of bad payloads via api requests
    private DbError updateData(long userDataId, long userId, String concatSectors, String name) {
        try {
            // if name was changed in the input then update it too
            if (userDao.getUserNameByUserId(userId).equals(name)) {
                this.userDao.updateOnlyUserData(userDataId, userId, concatSectors);
            } else {
                this.userDao.updateUserDataAndOrName(userDataId, userId, concatSectors, name);
            }
            return DbError.None;
            // Some error responses exposed api endpoints (prevent reverse-engineering)
        } catch (SQLException e) {
            logger.error("updateData", e);
        }
        return DbError.GenericError;
    }

    // if sectors that are in the payload are wrong (api request)
    private boolean checkReqSectors(UserInputData userReq) {
        List<Integer> wrongSectors = new ArrayList<>();
        for (int i = 0; i < userReq.sectors.size(); i++) {
            var sectorValue = userReq.sectors.get(i);
            if (sectorsDao.getSectors(sectorValue).isEmpty()) {
                wrongSectors.add(sectorValue);
            }
        }
        return wrongSectors.isEmpty();
    }

    public DbError updateOrInsertUserData(UserInputData userReq) {

        // if there is no data for the userData in this session (check by uid)
        if (userDao.getUserData(userReq.uid).isEmpty()) {
            return insertData(userReq);
        } else {
            String concatSectors = Utils.concatSectors(userReq.sectors);
            String getSectors = Utils.getSectors(userDao.getSectors(userReq.uid));

            long userId = userDao.getUserId(userReq.uid);
            long userDataId = userDao.getUserDataId(userReq.uid);
            String userName = userDao.getUserNameByUserId(userId);

            // if there is data for this session, then compare name and sectors
            if (getSectors.compareToIgnoreCase(concatSectors) != 0 || !userName.equals(userReq.name)) {

                // do not want to give the error to the client side due to security reasons
                if (userId != 0 && userDataId != 0) {

                    // if there was no updated data, update will not invoke
                    return updateData(userDataId, userId, concatSectors, userReq.name);
                }
            }
        }
        return DbError.None;
    }

    // delete data via api request /api/userData/{name}
    public void deleteDataByUserName(String userName) {
        long userId = userDao.getUserIdByName(userName);
        if (userId != 0) {
            this.userDao.deleteUserAndUserData(userId);
        }
    }
}
