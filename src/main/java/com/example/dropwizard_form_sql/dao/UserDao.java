package com.example.dropwizard_form_sql.dao;


import com.example.dropwizard_form_sql.mapper.UserDataRowMapper;
import com.example.dropwizard_form_sql.mapper.UserRowMapper;
import com.example.dropwizard_form_sql.model.User;
import com.example.dropwizard_form_sql.model.UserData;
import org.jdbi.v3.core.JdbiException;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    // variable names are self-explanatory I guess

    String insertUserData = "insert into user_data (uid, sectors, terms, user_id) " +
                            "values (:uid, :concatSectors, :terms, :userId)";

    String insertUserName = "insert into users (name) values (:name)";

    String getUserIdByUid = "select user_id from user_data where uid = :uid";

    String getUserIdByName = "select user_id from users where name = :name";

    String getUserDataId = "select user_data_id from user_data where uid = :uid";

    String getSectors = "select sectors from user_data where uid = :uid";

    String getUserByName = "select * from users where name = :name";

    String getUserData = "select * from user_data where uid = :uid";

    String getUserNameByUserId = "select name from users where user_id = :userId ";

    String updateOnlyUserData = "update user_data set sectors = :concatSectors where user_id = :userId " +
                            "and user_data_id = :userDataId";

    String updateUserName = "update users set name = :name where user_id = :userId";

    String deleteUser = "delete from users where user_id  = :userId";

    String deleteUserData = "delete from user_data where user_id = :userId";

    @SqlUpdate(insertUserData)
    @GetGeneratedKeys
    long insertUserData(
            @Bind("userId") long userId,
            @Bind("uid") String uid,
            @Bind("concatSectors") String concatSectors,
            @Bind("terms") Boolean terms
            );

    @SqlUpdate(insertUserName)
    @GetGeneratedKeys
    long insertUserName(@Bind("name") String name);

    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    default void insertData(
            String uid,
            String name,
            String concatSectors,
            Boolean terms) throws JdbiException
    {
        var userId = insertUserName(name);

        insertUserData(userId, uid, concatSectors, terms);

    }

    @SqlQuery(getUserByName)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserRowMapper.class)
    List<User> getUserByName(@Bind("name") String name);

    @SqlQuery(getUserIdByUid)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserRowMapper.class)
    long getUserId(@Bind("uid") String uid);

    @SqlQuery(getUserDataId)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserRowMapper.class)
    long getUserDataId(@Bind("uid") String uid);

    @SqlQuery(getSectors)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserRowMapper.class)
    List<String> getSectors(@Bind("uid") String uid);

    @SqlQuery(getUserIdByName)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserRowMapper.class)
    long getUserIdByName(@Bind("name") String name);

    @SqlQuery(getUserData)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserDataRowMapper.class)
    List<UserData> getUserData(@Bind("uid") String uid);

    @SqlQuery(getUserNameByUserId)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    @RegisterRowMapper(UserRowMapper.class)
    String getUserNameByUserId(@Bind("userId") long userId);

    @SqlUpdate(updateOnlyUserData)
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    void updateOnlyUserData(@Bind("userDataId") long userDataId,
                        @Bind("userId") long userId,
                        @Bind("concatSectors") String concatSectors) throws SQLException;

    @SqlUpdate(updateOnlyUserData)
    void updateUserData(@Bind("userDataId") long userDataId,
                        @Bind("userId") long userId,
                        @Bind("concatSectors") String concatSectors) throws SQLException;

    @SqlUpdate(updateUserName)
    void updateUserName(@Bind("name") String name,
                        @Bind("userId") long userId) throws SQLException;

    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    default void updateUserDataAndOrName(long userDataId,
                                         long userId,
                                         String concatSectors,
                                         String name) throws SQLException
    {
        updateUserName(name, userId);
        updateUserData(userDataId, userId, concatSectors);
    }

    @SqlUpdate(deleteUser)
    void deleteUser(@Bind("userId") long userId);

    @SqlUpdate(deleteUserData)
    void deleteUserData(@Bind("userId") long userId);

    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    default void deleteUserAndUserData(long userId) {
        deleteUserData(userId);
        deleteUser(userId);
    }
}
