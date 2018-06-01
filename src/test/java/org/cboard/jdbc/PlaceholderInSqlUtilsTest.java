package org.cboard.jdbc;

import org.cboard.dto.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class PlaceholderInSqlUtilsTest {

    User user;

    @Before
    public void setUp() throws Exception {

        user = new User("userA", "password", Collections.emptyList());
        user.setUserId("1");

    }

    @Test
    public void replacePlaceholderForUserid() {

        String rawSql = "select * from dashboard_user where user_id = #USER_ID";

        PlaceholderInSqlUtils sqlUtils = new PlaceholderInSqlUtils(rawSql, user);

        assertEquals("select * from dashboard_user where user_id = 1", sqlUtils.parse());
    }
}