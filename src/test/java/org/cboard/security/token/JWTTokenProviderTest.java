package org.cboard.security.token;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class JWTTokenProviderTest {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProviderTest.class);

    @Test
    public void encode() throws UnsupportedEncodingException {
        String loginName = "admin";
        JWTTokenProvider tokenProvider = new JWTTokenProvider();
        String token = tokenProvider.encode(loginName);

        logger.info("loginName: {}, token: {}", loginName, token);
        assertNotNull(token);
    }

    @Test
    public void verify() throws UnsupportedEncodingException {

        JWTTokenProvider tokenProvider = new JWTTokenProvider();
        String token = tokenProvider.encode("admin");

        String loginName = tokenProvider.verify(token);

        assertEquals("admin", loginName);
    }
}