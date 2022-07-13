package com.prices.security.auth.jwt;

import com.prices.config.CustomRedirectStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.prices.util.Constants.LOGIN_PATH;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final CustomRedirectStrategy redirectStrategy;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.error("Unauthorized error: {}", authException.getMessage());
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error: Forbidden");
        redirectStrategy.sendRedirect(request, response, LOGIN_PATH);
//        response.sendRedirect(LOGIN_PATH);
    }
}
