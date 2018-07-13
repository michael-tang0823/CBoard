package org.cboard.security.token;

import org.cboard.dto.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {
    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void onSuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws UnsupportedEncodingException {

        User user = (User) authentication.getPrincipal();

        JWTTokenProvider tokenProvider = new JWTTokenProvider();
        String token = tokenProvider.encode(user.getName());
        response.setHeader("Token" , token);

    }
}
