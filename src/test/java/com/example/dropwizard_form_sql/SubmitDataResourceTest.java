package com.example.dropwizard_form_sql;

import com.example.dropwizard_form_sql.dao.SectorsDao;
import com.example.dropwizard_form_sql.dao.UserDao;
import com.example.dropwizard_form_sql.model.Sector;
import com.example.dropwizard_form_sql.model.UserInputData;
import com.example.dropwizard_form_sql.resource.SubmitDataResource;
import com.example.dropwizard_form_sql.utils.ApiDefinitions;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SubmitDataResourceTest {
    private static final UserDao userDao = mock(UserDao.class);
    private static final SectorsDao sectorsDao = mock(SectorsDao.class);
    private static final ResourceExtension ext = ResourceExtension.builder()
            .addResource(new SubmitDataResource(sectorsDao, userDao))
            // TODO add config yml for logs
            //.addResource(ResourceHelpers.resourceFilePath("dropwizard-config.yml"))
            .build();

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void tearDown() {
        Mockito.reset(userDao, sectorsDao);
    }

    @Test
    public void submitEmptyUserRequest() {
        var req = new UserInputData();
        req.sectors = new ArrayList<>();

        var response = ext.target(ApiDefinitions.UserData).request().post(Entity.json(req));
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        verifyNoInteractions(userDao);
        verifyNoInteractions(sectorsDao);
    }

    @Test
    public void submitNewUserRequest() throws SQLException {
        var req = new UserInputData();
        req.sectors = new ArrayList<>();
        req.sectors.add(11);
        req.name = "test";
        req.terms = "true";

        ArgumentCaptor<String> uidArgCaptor = ArgumentCaptor.forClass(String.class);

        List<Sector> sectorsList = new ArrayList<>();
        sectorsList.add(new Sector(1, 0, 11, 0, "sector_1", 0));
        when(sectorsDao.getSectors(11)).thenReturn(sectorsList);
        when(userDao.getUserData(any())).thenReturn(new ArrayList<>());

        var response = ext.target(ApiDefinitions.UserData).request().post(Entity.json(req));
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.CREATED.getStatusCode());

        verify(sectorsDao, times(1)).getSectors(11);
        // verify userDao
        verify(userDao,
                times(1)).insertData(
                        uidArgCaptor.capture(),
                        eq(req.name),
                        eq("11"),
                        eq(true)
                );
        var uid = uidArgCaptor.getValue();
        assertThat(response.getHeaderString("Location")).contains(uid);

        verify(userDao, times(1)).getUserData(any());

        verifyNoMoreInteractions(userDao);
        verifyNoMoreInteractions(sectorsDao);
    }

    // TODO
    // I did many manual tests and seems like everything works

/*    @Test
    public void updateUserRequest() throws SQLException {

    }

    @Test
    public void deleteUser() {

    }*/
}
