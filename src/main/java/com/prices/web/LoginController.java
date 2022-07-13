package com.prices.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.prices.util.Constants.LOGIN_PATH;
import static com.prices.util.Constants.REGISTER_PATH;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping(LOGIN_PATH)
    public String login() {
        return "login";
    }

    @GetMapping(REGISTER_PATH)
    public String register() {
        return "register";
    }
}