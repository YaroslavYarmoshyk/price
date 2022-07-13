package com.prices.filter;

import com.prices.security.auth.jwt.JwtProvider;
import com.prices.service.EncryptingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.prices.util.Constants.AUTHENTICATION_PATH;
import static com.prices.util.Constants.FAVICON;
import static com.prices.util.Constants.JWT_NAME;
import static com.prices.util.Constants.LOGIN_PATH;
import static com.prices.util.Constants.REGISTER_PATH;
import static com.prices.util.Constants.REGISTRATION_PATH;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final EncryptingService encryptingService;
    private final List<String> notCheckedUrls;

    public CustomAuthorizationFilter(JwtProvider jwtProvider,
                                     EncryptingService encryptingService) {
        this.jwtProvider = jwtProvider;
        this.encryptingService = encryptingService;
        notCheckedUrls = new ArrayList<>();
        notCheckedUrls.add(AUTHENTICATION_PATH);
        notCheckedUrls.add(LOGIN_PATH);
        notCheckedUrls.add(REGISTER_PATH);
        notCheckedUrls.add(REGISTRATION_PATH);
        notCheckedUrls.add(FAVICON);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isCheckedRequest(request)) {
            String token = getToken(request);

            if (Objects.nonNull(token) && jwtProvider.validate(token)) {
                Authentication auth = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isCheckedRequest(HttpServletRequest request) {
        return notCheckedUrls.stream()
                .noneMatch(s -> request.getServletPath().startsWith(s));
    }

    private String getToken(HttpServletRequest request) {
        WebUtils.getCookie(request, JWT_NAME);
        if (Objects.isNull(request.getCookies())) {
            return null;
        }

        return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(JWT_NAME))
                .map(c -> encryptingService.decryptJwt(c.getValue()))
                .collect(Collectors.joining());
    }
}
