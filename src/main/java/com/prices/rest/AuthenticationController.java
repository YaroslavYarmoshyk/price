package com.prices.rest;

import com.prices.config.CustomRedirectStrategy;
import com.prices.payload.request.LoginRequest;
import com.prices.payload.request.SignupRequest;
import com.prices.security.auth.AuthenticationService;
import com.prices.service.CookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieService cookieService;
    private final CustomRedirectStrategy redirectStrategy;

    @PostMapping("/signin")
    public void authenticateUser(@Valid @ModelAttribute LoginRequest loginRequest,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws IOException {
        String jwt = authenticationService.authenticateUser(loginRequest);
        cookieService.setJwtCookie(jwt, response);
        redirectStrategy.sendRedirect(request, response, "/api/users");
    }

    @PostMapping("/signup")
    public void registerUser(@Valid @ModelAttribute SignupRequest signupRequest,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        authenticationService.registerUser(signupRequest, request);
        redirectStrategy.sendRedirect(request, response, "/login");
    }
}
