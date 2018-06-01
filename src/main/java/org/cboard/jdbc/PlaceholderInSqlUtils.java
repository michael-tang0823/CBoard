package org.cboard.jdbc;

import org.cboard.dataprovider.config.ConfigComponent;
import org.cboard.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlaceholderInSqlUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceholderInSqlUtils.class);

    String rawSql;

    User user;

    public PlaceholderInSqlUtils(String rawSql, User user) {
        this.rawSql = rawSql;
        this.user = user;
    }

    public String parse() {

        LOG.debug("--------------------------------------------------------------");
        LOG.debug("original sql: {}", rawSql);

        String result = replacePlaceholderForUser(rawSql);

        LOG.debug("--------------------------------------------------------------");
        LOG.debug("parsed sql: {}", result);

        return result;
    }

    public String parse(List<ConfigComponent> filters) {

        LOG.debug("--------------------------------------------------------------");
        LOG.debug("original sql: {}", rawSql);

        String result = replacePlaceholderForUser(rawSql);

        LOG.debug("--------------------------------------------------------------");
        LOG.debug("parsed sql: {}", result);

        return result;
    }

    /**
     * replace placeholder with real tenant info
     * @param rawSql
     * @return
     */
    private String replacePlaceholderForUser(String rawSql) {
        if (rawSql == null) return null;

        rawSql = rawSql.replaceAll(PlaceholderConstant.PLACEHOLDER_USER_ID, user.getUserId());

        return rawSql;
    }
}
