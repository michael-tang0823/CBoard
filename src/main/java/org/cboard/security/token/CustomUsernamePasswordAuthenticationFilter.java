package org.cboard.security.token;

import org.cboard.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();

        JWTTokenProvider tokenProvider = new JWTTokenProvider();
        String token = tokenProvider.encode(user.getName());
        response.setHeader("Token" , token);

        super.successfulAuthentication(request, response, chain, authentication);


    }
}
