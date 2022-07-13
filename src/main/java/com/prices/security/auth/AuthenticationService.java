package com.prices.security.auth;

import com.prices.model.User;
import com.prices.payload.request.LoginRequest;
import com.prices.payload.request.SignupRequest;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    String authenticateUser(LoginRequest loginRequest);

    User registerUser(SignupRequest signUpRequest, HttpServletRequest request);

}
