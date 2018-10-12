package org.cboard.security.token;


import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomRedirectStrategy extends DefaultRedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        super.sendRedirect(request, response, url);
    }
}
