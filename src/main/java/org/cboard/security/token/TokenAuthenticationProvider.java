package org.cboard.security.token;

import org.cboard.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        final String token = (String)authentication.getCredentials();


        String loginName = null;
        try {
            JWTTokenProvider tokenProvider = new JWTTokenProvider();
            loginName = tokenProvider.verify(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            new UsernameNotFoundException("Cannot find user with authentication token=" + token);
        }

        //load the user info and its authorities
        User user = (User)userDetailsService.loadUserByUsername(loginName);

        return user;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
