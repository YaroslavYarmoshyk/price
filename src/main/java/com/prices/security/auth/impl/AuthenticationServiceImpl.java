package com.prices.security.auth.impl;

import com.prices.exception.AuthenticationException;
import com.prices.exception.SystemException;
import com.prices.mapper.OrikaBeanMapper;
import com.prices.model.Role;
import com.prices.model.User;
import com.prices.model.dto.UserRequestDto;
import com.prices.payload.request.LoginRequest;
import com.prices.payload.request.SignupRequest;
import com.prices.security.PasswordEncoder;
import com.prices.security.auth.AuthenticationService;
import com.prices.security.auth.jwt.JwtProvider;
import com.prices.service.UserService;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final OrikaBeanMapper mapper;

    @Override
    @Transactional
    public String authenticateUser(LoginRequest loginRequest) {
        if (validateUserData(loginRequest)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
            log.info("User authenticated");

            return jwtProvider.createToken(loginRequest.getUsername(), roles);
        }

        throw new AuthenticationException("Invalid password or email");
    }

    @Override
    public User registerUser(SignupRequest signUpRequest, HttpServletRequest request) {
        String email = signUpRequest.getUsername();
        //Check if the email is not taken
        if (userService.findByEmail(email).isPresent()) {
            final SystemException systemException = new SystemException(String.format("Email %s is already in use!", email));
            request.setAttribute("errorMessage", systemException.getMessage());
            throw systemException;
        }
        //Check if password and repeat password are equal
        if (!checkPasswordsMatch(signUpRequest)) {
            final SystemException systemException = new SystemException("Password doesn't match repeat password");
            request.setAttribute("errorMessage", systemException.getMessage());
            throw systemException;
        }

        //Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword());
        Set<Role> rolesFromUser = signUpRequest.getRoles();
        Set<Role> roles = Objects.isNull(rolesFromUser) || rolesFromUser.isEmpty() ? Set.of(Role.USER) : rolesFromUser;

        user.setRoles(roles);

        return userService.save(user);
    }


    private boolean validateUserData(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Check if username and password aren't empty
        if (!Strings.hasLength(username) || !Strings.hasLength(password)) {
            log.warn("Username or password is empty");
            return false;
        }

        // Check if user exist
        UserRequestDto userDto = mapper.map(userService.findByEmail(username).orElse(null), UserRequestDto.class);
        if (Objects.isNull(userDto)) {
            log.warn("User with username {} not found", username);
            return false;
        }

        // Check if password correct
        if (userDto.getPassword().equals(passwordEncoder.encode(password))) {
            log.warn("Invalid password was entered");
            return false;
        }

        return true;
    }

    private boolean checkPasswordsMatch(SignupRequest signupRequest) {
        return signupRequest.getPassword().equals(signupRequest.getRepeatPassword());
    }

}
