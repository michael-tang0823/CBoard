package org.cboard.security.token;

import org.cboard.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public static final String SPRING_SECURITY_URL_TOKEN_KEY = "token";

    @Autowired
    JdbcDaoImpl userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getParameter(SPRING_SECURITY_URL_TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String loginName = null;
        try {
            JWTTokenProvider tokenProvider = new JWTTokenProvider();
            loginName = tokenProvider.verify(token);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (StringUtils.isEmpty(loginName)) {
            filterChain.doFilter(request, response);
            return;
        }

        //load the user info and its authorities
        User user = (User)userDetailsService.loadUserByUsername(loginName);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }
}
