package org.cboard.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class JWTTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String encode(String userName) throws UnsupportedEncodingException {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("APM")
                    .withSubject(userName)
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public String verify(String token) throws UnsupportedEncodingException, JWTVerificationException {

        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("APM")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
