package com.prices.service.impl;

import com.prices.service.CookieService;
import com.prices.service.EncryptingService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.prices.util.Constants.JWT_NAME;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class CookieServiceImpl implements CookieService {
    private final EncryptingService encryptingService;

    public CookieServiceImpl(EncryptingService encryptingService) {
        this.encryptingService = encryptingService;
    }

    @Override
    public void setJwtCookie(String jwt, HttpServletResponse response) {
        Cookie cookie = new Cookie(JWT_NAME, encryptingService.encryptJwt(jwt));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader(AUTHORIZATION, "Bearer " + encryptingService.encryptJwt(jwt));
    }
}
