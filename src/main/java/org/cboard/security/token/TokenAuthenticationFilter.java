package org.cboard.security.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private static final String SPRING_SECURITY_URL_TOKEN_KEY = "Token";
    private static final String BEARER = "Bearer ";

    public TokenAuthenticationFilter() {
        super(new OrRequestMatcher(new AntPathRequestMatcher("/commons/**"), new AntPathRequestMatcher("/dashboard/**"), new AntPathRequestMatcher("/admin/**")));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //get token from header first, if not found then from url
        Optional<String> authToken = Optional.ofNullable(request.getHeader("Authorization"))
                .filter(s -> s.length() > BEARER.length() && s.startsWith(BEARER))
                .map(s -> s.substring(BEARER.length(), s.length()));

        if (!authToken.isPresent()) {
            authToken = Optional.ofNullable(request.getHeader(SPRING_SECURITY_URL_TOKEN_KEY));
        }

        if (!authToken.isPresent()) {
            authToken = Optional.ofNullable(request.getParameter(SPRING_SECURITY_URL_TOKEN_KEY));
        }

        if (!authToken.isPresent()) {
            throw new BadCredentialsException("Missing Authentication Token");
        }


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, authToken.get());
        return getAuthenticationManager().authenticate(authentication);

    }
}
