package com.prices.service;

import com.prices.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> getAllUsers();

    Optional<User> getById(Long id);

    Optional<User> findByEmail(String email);
}
