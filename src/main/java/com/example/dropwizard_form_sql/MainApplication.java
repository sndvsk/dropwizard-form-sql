package com.example.dropwizard_form_sql;

import com.example.dropwizard_form_sql.resource.ApplicationResource;
import com.example.dropwizard_form_sql.resource.SubmitDataResource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.example.dropwizard_form_sql.dao.SectorsDao;
import com.example.dropwizard_form_sql.dao.UserDao;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.sql.DataSource;
import java.sql.SQLException;

public class MainApplication extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new MainApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizardTask";
    }

    @Override
    public void run(ApplicationConfiguration basicConfiguration, Environment environment) throws SQLException {
        //register classes
        var jdbi = getJdbi();
        var sectorsDao = jdbi.onDemand(SectorsDao.class);
        var userDao = jdbi.onDemand(UserDao.class);

        environment.jersey().register(new ApplicationResource(sectorsDao));
        environment.jersey().register(new SubmitDataResource(sectorsDao, userDao));
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/com/example/dropwizard_form_sql/assets/", "/assets", "index.html"));
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }

    private static Jdbi getJdbi() throws SQLException {
        var jdbi = Jdbi.create(dataSource());
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    private static DataSource dataSource() {
        // TODO
        // obtain password property through System.getenv().get(,,,) instead of
        // hardcoded in jar/resource
        HikariConfig config = new HikariConfig("/hikari.properties");
        return new HikariDataSource(config);
    }

}
