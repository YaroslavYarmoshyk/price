package com.prices.service;

import com.prices.model.Role;
import com.prices.model.User;
import com.prices.repository.UserRepository;
import com.prices.security.PasswordEncoder;
import com.prices.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for UserService")
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final User user = new User();

    @BeforeAll
    static void beforeAll() {
        user.setEmail("bob@i.ua");
        user.setPassword("3233");
    }

    @Test
    @DisplayName("Test saving user")
    void saveUser() {
        User expected = user;
        expected.setId(1L);
        expected.setActive(true);
        expected.setRoles(Set.of(Role.USER));

        user.setId(1L);
        user.setActive(true);
        user.setRoles(Set.of(Role.USER));

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.save(user);

        User actual = userService.getById(1L).orElse(null);

        assertEquals(expected, actual);

    }
}