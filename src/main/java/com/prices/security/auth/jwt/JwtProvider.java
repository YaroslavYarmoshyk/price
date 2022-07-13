package com.prices.security.auth.jwt;

import com.prices.exception.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface JwtProvider {

    String createToken(String username, Collection<? extends GrantedAuthority> roles);

    String getUsername(String token);

    String resolveToken(HttpServletRequest servletRequest);

    boolean validate(String token) throws AuthenticationException;

    Authentication getAuthentication(String token);
}
