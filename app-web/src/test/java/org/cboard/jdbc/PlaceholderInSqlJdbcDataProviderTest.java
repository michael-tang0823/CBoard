package org.cboard.jdbc;

import org.cboard.dataprovider.config.AggConfig;
import org.cboard.dataprovider.config.DimensionConfig;
import org.cboard.dataprovider.config.ValueConfig;
import org.cboard.dto.User;
import org.cboard.services.AuthenticationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaceholderInSqlJdbcDataProviderTest {

    PlaceholderInSqlJdbcDataProvider provider = new PlaceholderInSqlJdbcDataProvider();

    @Before
    public void setUp() throws Exception {

        Map<String, String> datasource = new HashMap();
        datasource.put("driver", "org.postgresql.Driver");
        datasource.put("jdbcurl", "jdbc:postgresql://localhost:5432/cboard");
        datasource.put("username", "postgres");
        datasource.put("password", "root");

        AuthenticationService authService = mock(AuthenticationService.class);
        when(authService.getCurrentUser()).thenReturn(new User("test", "test", Collections.emptyList()));

        provider = new PlaceholderInSqlJdbcDataProvider();
        provider.setDataSource(datasource);
        provider.setAuthenticationService(authService);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void test() throws Exception {

        Map<String, String> query = new HashMap();
        query.put("sql", "select 1");
        provider.setQuery(query);
        provider.test();
    }

    @Test
    public void getColumn() throws Exception {
        Map<String, String> query = new HashMap();
        query.put("sql", "select * from dashboard_user");
        provider.setQuery(query);
        String[] columns = provider.getColumn(true);

        assertTrue(columns.length > 0);
    }

    @Test
    public void getData() {
    }


    @Test
    public void queryAggData() throws Exception {

        Map<String, String> query = new HashMap();
        query.put("sql", "select * from dashboard_user");
        provider.setQuery(query);
        provider.afterPropertiesSet();

        DimensionConfig rowDimConfig = new DimensionConfig();
        rowDimConfig.setColumnName("login_name");
        rowDimConfig.setFilterType("eq");
        rowDimConfig.setValues(Collections.emptyList());

        List<DimensionConfig> rows = new ArrayList<>();
        rows.add(rowDimConfig);

//        DimensionConfig colDimConfig = new DimensionConfig();
//        List<DimensionConfig> cols = new ArrayList<>();
//        rows.add(colDimConfig);

        ValueConfig valueConfig = new ValueConfig();
        valueConfig.setAggType("count");
        valueConfig.setColumn("login_name");
        List<ValueConfig> values = new ArrayList<>();
        values.add(valueConfig);

        AggConfig aggConfig = new AggConfig();
        aggConfig.setColumns(Collections.emptyList());
        aggConfig.setFilters(Collections.emptyList());
        aggConfig.setRows(rows);
        aggConfig.setValues(values);


        provider.queryAggData(aggConfig);
    }
}