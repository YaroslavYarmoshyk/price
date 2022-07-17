package com.prices.security;

import com.prices.config.CustomRedirectStrategy;
import com.prices.exception.AuthenticationException;
import com.prices.model.dto.HttpErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static com.prices.util.Constants.LOGIN_PATH;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ExceptionTranslator {
    private final CustomRedirectStrategy redirectStrategy;

    public ExceptionTranslator(CustomRedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public HttpErrorResponseDTO handleException(final Exception e) {
        log.error(INTERNAL_SERVER_ERROR.toString());

        return new HttpErrorResponseDTO(INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public HttpErrorResponseDTO handleAccessDeniedException(final AccessDeniedException e) {
        log.error(FORBIDDEN.toString());

        return new HttpErrorResponseDTO(FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public void handleBadCredentialsException(final BadCredentialsException e,
                                              final HttpServletRequest request,
                                              final HttpServletResponse response) throws IOException {
        log.error("Username or password invalid");
        redirectStrategy.sendRedirect(request, response, LOGIN_PATH);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public void handleAuthenticationException(final AuthenticationException e,
                                              final HttpServletRequest request,
                                              final HttpServletResponse response) throws IOException {
        log.error("Username or password invalid");
        redirectStrategy.sendRedirect(request, response, LOGIN_PATH);
    }
}
