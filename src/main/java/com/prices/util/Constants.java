package com.prices.util;

public final class Constants {
    public static final String AUTHENTICATION_PATH = "/api/auth/signin";
    public static final String REGISTRATION_PATH = "/api/auth/signup";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/registration";
    public static final String LOGOUT_PATH = "/api/logout";
    public static final String FAVICON = "/favicon.ico";

    //Tokens
    //    TODO need to be encrypted/decrypted
    public static final String SECRET = "eprice";
    public static long TOKEN_VALIDITY_SECONDS = 10 * 60 * 1000;

    public static final String JWT_NAME = "J";

    //Annotation
    public static final String ADMIN_ACCESS_ONLY = "'hasAuthority:ADMIN'";

    //Keys
    public static final String ENCRYPTION_KEY = "enctyptioneprice";

}
