package com.prices.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.prices.util.Constants.ADMIN_ACCESS_ONLY;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@PreAuthorize(ADMIN_ACCESS_ONLY)
public @interface AdminAccessLevel {
}
