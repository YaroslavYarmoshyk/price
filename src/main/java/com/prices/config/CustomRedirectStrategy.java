package com.prices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomRedirectStrategy implements RedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request,
                             HttpServletResponse response,
                             String url) throws IOException {
        response.sendRedirect(url);
    }
}
