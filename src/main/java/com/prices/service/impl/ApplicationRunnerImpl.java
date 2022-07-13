package com.prices.service.impl;

import com.prices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final UserService userService;

    @Autowired
    public ApplicationRunnerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        User yaroslav = new User("yarmoshyk96@gmail.com","Qwerty1!");
//        yaroslav.setRoles(Set.of(Role.ADMIN));
//        User alice = new User("alice@i.ua","4321");
//        alice.setRoles(Set.of(Role.USER));
//        userService.save(yaroslav);
//        userService.save(alice);

    }
}
