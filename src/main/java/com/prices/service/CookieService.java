package com.prices.service;

import javax.servlet.http.HttpServletResponse;

public interface CookieService {

    void setJwtCookie(String jwt, HttpServletResponse response);
}
