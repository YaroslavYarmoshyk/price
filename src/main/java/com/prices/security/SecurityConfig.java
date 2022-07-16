package com.prices.security;

import com.prices.filter.CustomAuthorizationFilter;
import com.prices.security.auth.jwt.AuthEntryPoint;
import com.prices.security.auth.jwt.JwtProvider;
import com.prices.service.EncryptingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.prices.util.Constants.AUTHENTICATION_PATH;
import static com.prices.util.Constants.FAVICON;
import static com.prices.util.Constants.JWT_COOKIE_NAME;
import static com.prices.util.Constants.LOGIN_PATH;
import static com.prices.util.Constants.LOGOUT_PATH;
import static com.prices.util.Constants.REGISTER_PATH;
import static com.prices.util.Constants.REGISTRATION_PATH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final EncryptingService encryptingService;
    private final AuthEntryPoint unauthorizedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().authorizeRequests().antMatchers(LOGIN_PATH, AUTHENTICATION_PATH, REGISTER_PATH, REGISTRATION_PATH, FAVICON).permitAll()
                .and()
                .authorizeRequests().antMatchers(POST, "/api/users/**").hasAnyAuthority("ADMIN", "USER")
                .and()
                .addFilterBefore(new CustomAuthorizationFilter(jwtProvider, encryptingService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .logout()
                    .logoutUrl(LOGOUT_PATH)
                    .permitAll()
                    .deleteCookies("JSESSIONID")
                    .deleteCookies(JWT_COOKIE_NAME)
                    .invalidateHttpSession(true)
                .and()
                .headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
